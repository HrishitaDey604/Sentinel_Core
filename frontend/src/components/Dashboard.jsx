import React, { useState, useEffect } from 'react';
import './Dashboard.css';

export default function Dashboard() {

    const [summary, setSummary] = useState({
        totalAssets: 5,
        uptimePercentage: 99.8,
        onlineAssets: 4,
        offlineAssets: 1,
        criticalAlerts: 1,
        avgCpuUsage: 45.2,
        avgMemoryUsage: 58.6
    });

    const [assets, setAssets] = useState([
        { id: 1, name: 'WebServer-01', assetName: 'WebServer-01', assetType: 'Server', ipAddress: '192.168.1.10', cpuUsage: '45.2', memoryUsage: '60.1', diskUsage: '70.5', networkUsage: '30.2', status: 'ONLINE', uptime: '99.98%', kernel: 'Linux 6.8.0-generic', region: 'ap-south-1a' },
        { id: 2, name: 'DBServer-01', assetName: 'DBServer-01', assetType: 'Database', ipAddress: '192.168.1.11', cpuUsage: '78.5', memoryUsage: '82.3', diskUsage: '55.0', networkUsage: '20.1', status: 'WARNING', uptime: '98.42%', kernel: 'PostgreSQL 16.2', region: 'ap-south-1b' },
        { id: 3, name: 'Router-01', assetName: 'Router-01', assetType: 'Network', ipAddress: '192.168.1.1', cpuUsage: '12.0', memoryUsage: '25.0', diskUsage: '10.0', networkUsage: '55.0', status: 'ONLINE', uptime: '99.99%', kernel: 'EdgeOS v3.1', region: 'Gateway Edge' },
        { id: 4, name: 'AppServer-02', assetName: 'AppServer-02', assetType: 'Server', ipAddress: '192.168.1.12', cpuUsage: '92.0', memoryUsage: '88.0', diskUsage: '90.0', networkUsage: '40.0', status: 'CRITICAL', uptime: '94.10%', kernel: 'Ubuntu 24.04 LTS', region: 'ap-south-1c' }
    ]);


    const [alerts, setAlerts] = useState([
        { id: 1, message: 'High CPU utilization detected on AppServer-02 (>90%)', severity: 'CRITICAL', createdAt: 'Just now' },
        { id: 2, message: 'Database memory pressure exceeding 80% threshold', severity: 'WARNING', createdAt: '10m ago' }
    ]);

    const [selectedAsset, setSelectedAsset] = useState(null);
    const [isRestarting, setIsRestarting] = useState(false);
    const [searchQuery, setSearchQuery] = useState('');
    const [statusFilter, setStatusFilter] = useState('ALL');


    const [userRole, setUserRole] = useState('ROLE_ADMIN');

    useEffect(() => {
        const token = localStorage.getItem('accessToken') || localStorage.getItem('token');
        const headers = token ? { 'Authorization': `Bearer ${token}` } : {};

        if (token) {
            try {
                const payload = JSON.parse(atob(token.split('.')[1]));
                if (payload.roles || payload.role) {
                    const r = payload.roles || payload.role;
                    setUserRole(Array.isArray(r) ? r[0] : r);
                }
            } catch (e) {
                setUserRole('ROLE_ADMIN');
            }
        }


        fetch('http://localhost:8080/api/dashboard/summary', { headers })
            .then(res => res.ok ? res.json() : null)
            .then(data => {
                if (data) setSummary(prev => ({ ...prev, ...data }));
            })
            .catch(() => {});


        fetch('http://localhost:8080/api/assets', { headers })
            .then(res => res.ok ? res.json() : null)
            .then(data => {
                if (Array.isArray(data) && data.length > 0) setAssets(data);
            })
            .catch(() => {});


        fetch('http://localhost:8080/api/alerts', { headers })
            .then(res => res.ok ? res.json() : null)
            .then(data => {
                if (Array.isArray(data) && data.length > 0) setAlerts(data);
            })
            .catch(() => {});
    }, []);

    const handleLogout = () => {
        localStorage.clear();
        window.location.href = '/login';
    };

    const getMeterColor = (val) => {
        const num = parseFloat(val) || 0;
        if (num >= 90) return 'var(--meter-critical)';
        if (num >= 70) return 'var(--meter-warning)';
        return 'var(--meter-normal)';
    };

    const getStatusClass = (status) => {
        switch ((status || '').toUpperCase()) {
            case 'ONLINE': return 'status-badge status-online';
            case 'WARNING': return 'status-badge status-warning';
            case 'CRITICAL': return 'status-badge status-critical';
            default: return 'status-badge';
        }
    };

    const handleRestartService = (asset) => {
        if (!asset || isRestarting) return;
        setIsRestarting(true);

        setTimeout(() => {
            setAssets(prev =>
                prev.map(a =>
                    a.id === asset.id
                        ? { ...a, status: 'ONLINE', cpuUsage: '15.4', memoryUsage: '26.0' }
                        : a
                )
            );
            setSelectedAsset(prev =>
                prev && prev.id === asset.id
                    ? { ...prev, status: 'ONLINE', cpuUsage: '15.4', memoryUsage: '26.0' }
                    : prev
            );
            setIsRestarting(false);
        }, 1200);
    };

    const handleDecommission = (assetId) => {
        if (!window.confirm('Are you sure you want to decommission this node?')) return;
        setAssets(prev => prev.filter(a => a.id !== assetId));
        setSelectedAsset(null);
    };

    const filteredAssets = assets.filter((asset) => {
        const name = (asset.assetName || asset.name || '').toLowerCase();
        const type = (asset.assetType || asset.type || '').toLowerCase();
        const ip = (asset.ipAddress || '').toLowerCase();
        const query = searchQuery.toLowerCase().trim();

        const matchesQuery = name.includes(query) || ip.includes(query) || type.includes(query);
        const matchesStatus = statusFilter === 'ALL' || (asset.status || '').toUpperCase() === statusFilter;
        return matchesQuery && matchesStatus;
    });

    const isAdmin = userRole === 'ROLE_ADMIN' || userRole === 'ADMIN';

    return (
        <div className="dashboard-page">
            <header className="dashboard-header">
                <div className="brand-lockup">
                    <span className="brand-dot"></span>
                    <h1 className="brand-title">Sentinel Core</h1>
                </div>
                <div className="header-actions">
                    <span className="role-tag">{isAdmin ? 'ADMIN' : 'OPERATOR'}</span>
                    <button className="logout-button" onClick={handleLogout}>Log out</button>
                </div>
            </header>

            <main className="dashboard-container">

                {alerts.length > 0 && (
                    <section className="alerts-strip">
                        <div className="alerts-title-tag">ACTIVE INCIDENTS ({alerts.length})</div>
                        <div className="alerts-list">
                            {alerts.map(a => (
                                <div key={a.id} className={`alert-banner-item alert-${(a.severity || 'WARNING').toLowerCase()}`}>
                                    <span className="alert-badge">{a.severity}</span>
                                    <span className="alert-message">{a.message}</span>
                                    <span className="alert-time">{a.createdAt || ''}</span>
                                </div>
                            ))}
                        </div>
                    </section>
                )}

                <section className="dashboard-section">
                    <div className="section-title-wrapper">
                        <span className="section-bar"></span>
                        <h2 className="section-title">SYSTEM OVERVIEW</h2>
                    </div>

                    <div className="overview-grid">
                        <div className="overview-card">
                            <span className="overview-label">TOTAL ASSETS</span>
                            <span className="overview-val">{summary.totalAssets ?? assets.length}</span>
                        </div>
                        <div className="overview-card">
                            <span className="overview-label">ONLINE NODES</span>
                            <span className="overview-val text-online">{summary.onlineAssets ?? 0}</span>
                        </div>
                        <div className="overview-card">
                            <span className="overview-label">OFFLINE / WARNING</span>
                            <span className="overview-val text-warning">{summary.offlineAssets ?? 0}</span>
                        </div>
                        <div className="overview-card">
                            <span className="overview-label">SYSTEM UPTIME</span>
                            <span className="overview-val text-online">{summary.uptimePercentage ?? 99.9}%</span>
                        </div>
                    </div>
                </section>

                <section className="dashboard-section">
                    <div className="section-title-wrapper">
                        <span className="section-bar"></span>
                        <h2 className="section-title">TELEMETRY & USAGE</h2>
                    </div>

                    <div className="telemetry-grid">
                        <div className="telemetry-card">
                            <div className="telemetry-card-header">
                                <span className="telemetry-label">AVG CPU LOAD</span>
                                <span className="telemetry-val">{summary.avgCpuUsage ?? 0}%</span>
                            </div>
                            <div className="gauge-track">
                                <div
                                    className="gauge-fill"
                                    style={{
                                        width: `${Math.min(summary.avgCpuUsage ?? 0, 100)}%`,
                                        backgroundColor: getMeterColor(summary.avgCpuUsage),
                                        boxShadow: `0 0 10px ${getMeterColor(summary.avgCpuUsage)}`
                                    }}
                                />
                            </div>
                        </div>

                        <div className="telemetry-card">
                            <div className="telemetry-card-header">
                                <span className="telemetry-label">AVG MEMORY LOAD</span>
                                <span className="telemetry-val">{summary.avgMemoryUsage ?? 0}%</span>
                            </div>
                            <div className="gauge-track">
                                <div
                                    className="gauge-fill"
                                    style={{
                                        width: `${Math.min(summary.avgMemoryUsage ?? 0, 100)}%`,
                                        backgroundColor: getMeterColor(summary.avgMemoryUsage),
                                        boxShadow: `0 0 10px ${getMeterColor(summary.avgMemoryUsage)}`
                                    }}
                                />
                            </div>
                        </div>

                        <div className="telemetry-card">
                            <div className="telemetry-card-header">
                                <span className="telemetry-label">CRITICAL ALERTS</span>
                                <span className="telemetry-val text-critical">{summary.criticalAlerts ?? alerts.length}</span>
                            </div>
                            <div className="gauge-track">
                                <div
                                    className="gauge-fill"
                                    style={{
                                        width: summary.criticalAlerts ? '100%' : '0%',
                                        backgroundColor: summary.criticalAlerts ? 'var(--meter-critical)' : 'var(--meter-normal)'
                                    }}
                                />
                            </div>
                        </div>

                        <div className="telemetry-card">
                            <div className="telemetry-card-header">
                                <span className="telemetry-label">FLEET HEALTH</span>
                                <span className="telemetry-val text-online">SECURE</span>
                            </div>
                            <div className="gauge-track">
                                <div className="gauge-fill" style={{ width: '100%', backgroundColor: 'var(--meter-normal)' }} />
                            </div>
                        </div>
                    </div>
                </section>

                <section className="dashboard-section">
                    <div className="table-controls-header">
                        <div className="section-title-wrapper">
                            <span className="section-bar"></span>
                            <h2 className="section-title">MONITORED ASSETS</h2>
                        </div>

                        <div className="filter-toolbar">
                            <div className="search-box">
                                <span className="search-icon">🔍</span>
                                <input
                                    type="text"
                                    className="search-input"
                                    placeholder="Search by name, IP, or type..."
                                    value={searchQuery}
                                    onChange={(e) => setSearchQuery(e.target.value)}
                                />
                                {searchQuery && (
                                    <button className="search-clear-btn" onClick={() => setSearchQuery('')}>✕</button>
                                )}
                            </div>

                            <div className="status-filter-group">
                                {['ALL', 'ONLINE', 'WARNING', 'CRITICAL'].map((status) => (
                                    <button
                                        key={status}
                                        className={`filter-chip ${statusFilter === status ? 'chip-active' : ''}`}
                                        onClick={() => setStatusFilter(status)}
                                    >
                                        {status}
                                    </button>
                                ))}
                            </div>
                        </div>
                    </div>

                    <div className="table-card">
                        <table className="assets-table">
                            <thead>
                            <tr>
                                <th>NAME</th>
                                <th>TYPE</th>
                                <th>IP ADDRESS</th>
                                <th>CPU</th>
                                <th>MEMORY</th>
                                <th>STATUS</th>
                            </tr>
                            </thead>
                            <tbody>
                            {filteredAssets.length === 0 ? (
                                <tr><td colSpan="6" className="empty-cell">No assets found.</td></tr>
                            ) : (
                                filteredAssets.map((asset) => (
                                    <tr
                                        key={asset.id}
                                        className={`clickable-row ${selectedAsset?.id === asset.id ? 'row-active' : ''}`}
                                        onClick={() => setSelectedAsset(asset)}
                                    >
                                        <td className="font-medium text-white">{asset.assetName || asset.name}</td>
                                        <td className="text-muted">{asset.assetType || asset.type}</td>
                                        <td className="text-mono">{asset.ipAddress}</td>
                                        <td>{asset.cpuUsage}%</td>
                                        <td>{asset.memoryUsage}%</td>
                                        <td><span className={getStatusClass(asset.status)}>{asset.status}</span></td>
                                    </tr>
                                ))
                            )}
                            </tbody>
                        </table>
                    </div>
                </section>
            </main>

            <div className={`drawer-backdrop ${selectedAsset ? 'open' : ''}`} onClick={() => setSelectedAsset(null)} />
            <aside className={`asset-drawer ${selectedAsset ? 'open' : ''}`}>
                {selectedAsset && (
                    <div className="drawer-inner">
                        <div className="drawer-header">
                            <div>
                                <span className="drawer-tag">NODE INSPECTOR</span>
                                <h3 className="drawer-title">{selectedAsset.assetName || selectedAsset.name}</h3>
                            </div>
                            <button className="drawer-close-btn" onClick={() => setSelectedAsset(null)}>✕</button>
                        </div>

                        <div className="drawer-status-bar">
                            <span className={getStatusClass(selectedAsset.status)}>{selectedAsset.status}</span>
                            <span className="drawer-ip">{selectedAsset.ipAddress}</span>
                        </div>

                        <div className="drawer-section">
                            <h4 className="drawer-subhead">LIVE TELEMETRY</h4>
                            <div className="drawer-metrics-list">
                                <div className="drawer-metric-item">
                                    <div className="metric-meta">
                                        <span>CPU Load</span>
                                        <span className="metric-val">{selectedAsset.cpuUsage}%</span>
                                    </div>
                                    <div className="gauge-track">
                                        <div
                                            className="gauge-fill"
                                            style={{
                                                width: `${Math.min(parseFloat(selectedAsset.cpuUsage) || 0, 100)}%`,
                                                backgroundColor: getMeterColor(selectedAsset.cpuUsage)
                                            }}
                                        />
                                    </div>
                                </div>

                                <div className="drawer-metric-item">
                                    <div className="metric-meta">
                                        <span>Memory Load</span>
                                        <span className="metric-val">{selectedAsset.memoryUsage}%</span>
                                    </div>
                                    <div className="gauge-track">
                                        <div
                                            className="gauge-fill"
                                            style={{
                                                width: `${Math.min(parseFloat(selectedAsset.memoryUsage) || 0, 100)}%`,
                                                backgroundColor: getMeterColor(selectedAsset.memoryUsage)
                                            }}
                                        />
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div className="drawer-section">
                            <h4 className="drawer-subhead">NODE SPECIFICATIONS</h4>
                            <div className="drawer-spec-grid">
                                <div className="spec-card">
                                    <span className="spec-label">TYPE</span>
                                    <span className="spec-value">{selectedAsset.assetType || selectedAsset.type}</span>
                                </div>
                                <div className="spec-card">
                                    <span className="spec-label">UPTIME</span>
                                    <span className="spec-value">{selectedAsset.uptime || '99.9%'}</span>
                                </div>
                            </div>
                        </div>

                        {isAdmin ? (
                            <div className="drawer-actions">
                                <button
                                    className="action-btn action-restart"
                                    disabled={isRestarting}
                                    onClick={() => handleRestartService(selectedAsset)}
                                >
                                    {isRestarting ? 'Restarting Service...' : 'Restart Service'}
                                </button>
                                <button
                                    className="action-btn action-terminate"
                                    onClick={() => handleDecommission(selectedAsset.id)}
                                >
                                    Decommission
                                </button>
                            </div>
                        ) : (
                            <div className="read-only-notice">
                                🔒 Admin credentials required to perform node actions.
                            </div>
                        )}
                    </div>
                )}
            </aside>
        </div>
    );
}