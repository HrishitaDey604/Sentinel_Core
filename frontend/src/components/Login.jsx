import { useState } from 'react';
import { login } from '../api/authApi';

function Login({ onLoginSuccess }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [rememberMe, setRememberMe] = useState(false);
    const [error, setError] = useState(null);

    const handleSubmit = (e) => {
        e.preventDefault();
        setError(null);

        login(username, password)
            .then((res) => {
                // Reads "token" (from Spring Boot) or "accessToken" safely
                const token = res.data.token || res.data.accessToken;
                const refreshToken = res.data.refreshToken || '';

                if (!token) {
                    setError('Authentication token missing from response');
                    return;
                }

                localStorage.setItem('accessToken', token);
                if (refreshToken) {
                    localStorage.setItem('refreshToken', refreshToken);
                }
                if (rememberMe) {
                    localStorage.setItem('rememberMe', 'true');
                }
                onLoginSuccess();
            })
            .catch(() => {
                setError('Invalid username or password');
            });
    };

    return (
        <div className="login-page">
            <form className="login-card" onSubmit={handleSubmit}>
                <h1 className="login-heading">
                    <span className="login-heading-white">Sentinel</span>{' '}
                    <span className="login-heading-accent">Core</span>
                </h1>
                <p className="login-subtitle">Sign in to continue</p>

                {error && <p className="login-error">{error}</p>}

                <label className="login-label" htmlFor="username">Username</label>
                <input
                    id="username"
                    type="text"
                    className="login-input"
                    placeholder="ENTER USERNAME"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                />

                <label className="login-label" htmlFor="password">Password</label>
                <input
                    id="password"
                    type="password"
                    className="login-input"
                    placeholder="ENTER PASSWORD"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />

                <label className="login-remember">
                    <input
                        type="checkbox"
                        checked={rememberMe}
                        onChange={(e) => setRememberMe(e.target.checked)}
                    />
                    <span>Remember me</span>
                </label>

                <button type="submit" className="login-button">Log in</button>
            </form>
        </div>
    );
}

export default Login;