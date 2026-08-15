import { useEffect, useState } from 'react';
import { getDashboardSummary, getAllAssets } from '../api/assetApi';

function statusClass(status) {
    const s = status?.toUpperCase();
    if (s === 'ONLINE') return 'online';
    if (s === 'WARNING') return 'warning';
    if (s === 'CRITICAL') return 'critical';
    return '';
}

function Dashboard() {
    const [summary, setSummary] = useState(null);
    const [assets, setAssets] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        Promise.all([getDashboardSummary(), getAllAssets()])
            .then(([summaryRes, assetsRes]) => {
                setSummary(summaryRes.data);
                setAssets(assetsRes.data);
                setLoading(false);
            })
            .catch((err) => {
                setError(err.message);
                setLoading(false);
            });
    }, []);

    return (
        <div className="app-shell">
            <header className="header">
                <div className="eyebrow">
                    <span className="pulse-dot" />
                    System Online
                </div>
                <h1>Sentinel <span>Core</span></h1>
            </header>

            <main className="dashboard-body">
                {loading && <p className="state-message">Loading telemetry…</p>}
                {error && <p className="state-message">Connection failed — {error}</p>}

                {!loading && !error && summary && (
                    <>
                        <div className="section-label">Fleet Overview</div>
                        <div className="kpi-grid">
                            <div className="kpi-tile">
                                <div className="label">Total Assets</div>
                                <div className="value">{summary.totalAssets}</div>
                            </div>
                            <div className="kpi-tile online">
                                <div className="label">Online</div>
                                <div className="value">{summary.onlineCount}</div>
                            </div>
                            <div className="kpi-tile warning">
                                <div className="label">Warning</div>
                                <div className="value">{summary.warningCount}</div>
                            </div>
                            <div className="kpi-tile critical">
                                <div className="label">Critical</div>
                                <div className="value">{summary.criticalCount}</div>
                            </div>
                        </div>

                        <div className="section-label">Average Utilization</div>
                        <div className="kpi-grid">
                            <div className="kpi-tile">
                                <div className="label">CPU</div>
                                <div className="value">{summary.avgCpuUsage.toFixed(1)}%</div>
                            </div>
                            <div className="kpi-tile">
                                <div className="label">Memory</div>
                                <div className="value">{summary.avgMemoryUsage.toFixed(1)}%</div>
                            </div>
                            <div className="kpi-tile">
                                <div className="label">Disk</div>
                                <div className="value">{summary.avgDiskUsage.toFixed(1)}%</div>
                            </div>
                            <div className="kpi-tile">
                                <div className="label">Network</div>
                                <div className="value">{summary.avgNetworkUsage.toFixed(1)}%</div>
                            </div>
                        </div>

                        <div className="section-label">Assets</div>
                        <div className="asset-panel">
                            <table>
                                <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Type</th>
                                    <th>IP Address</th>
                                    <th>CPU</th>
                                    <th>Memory</th>
                                    <th>Disk</th>
                                    <th>Network</th>
                                    <th>Status</th>
                                </tr>
                                </thead>
                                <tbody>
                                {assets.map((asset) => (
                                    <tr key={asset.id}>
                                        <td style={{ color: 'var(--text)', fontWeight: 500 }}>{asset.assetName}</td>
                                        <td style={{ color: 'var(--muted)' }}>{asset.assetType}</td>
                                        <td style={{ color: 'var(--muted)' }}>{asset.ipAddress}</td>
                                        <td>{asset.cpuUsage}%</td>
                                        <td>{asset.memoryUsage}%</td>
                                        <td>{asset.diskUsage}%</td>
                                        <td>{asset.networkUsage}%</td>
                                        <td>
                        <span className={`status-badge ${statusClass(asset.status)}`}>
                          <span className="status-dot" />
                            {asset.status}
                        </span>
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>
                    </>
                )}
            </main>
        </div>
    );
}

export default Dashboard;