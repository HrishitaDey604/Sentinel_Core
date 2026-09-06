import { useState } from 'react'
import Login from './components/Login'
import Dashboard from './components/Dashboard'
import './App.css'

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(!!localStorage.getItem('accessToken'))

  if (!isLoggedIn) {
    return <Login onLoginSuccess={() => setIsLoggedIn(true)} />
  }

  return <Dashboard />
}

export default App