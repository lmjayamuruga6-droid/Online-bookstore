import React, { useState } from 'react';
import { login, register } from '../services/api';

const Auth = () => {
    const [isLogin, setIsLogin] = useState(true);
    const [form, setForm] = useState({username:'', password:'', email:''});
    const [token, setToken] = useState(localStorage.getItem('token'));

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const res = isLogin ? await login(form) : await register(form);
            localStorage.setItem('token', res.data.token);
            setToken(res.data.token);
            alert(isLogin ? 'Logged in' : 'Registered');
        } catch(err) { alert(err.response?.data?.message || 'Auth failed'); }
    };

    if(token) return <div style={{background:'#e3f2fd', padding:'10px'}}>Logged in. Token stored. <button onClick={()=>{localStorage.removeItem('token'); setToken(null);}}>Logout</button></div>;

    return (
        <div style={{background:'white', padding:'15px', borderRadius:'8px', marginBottom:'20px'}}>
            <h3>{isLogin ? 'Login' : 'Register'}</h3>
            <form onSubmit={handleSubmit}>
                <input placeholder="Username" value={form.username} onChange={e=>setForm({...form, username:e.target.value})} required />
                <input placeholder="Password" type="password" value={form.password} onChange={e=>setForm({...form, password:e.target.value})} required />
                {!isLogin && <input placeholder="Email" value={form.email} onChange={e=>setForm({...form, email:e.target.value})} />}
                <button type="submit">{isLogin ? 'Login' : 'Register'}</button>
            </form>
            <button onClick={()=>setIsLogin(!isLogin)} style={{background:'transparent', color:'#1976d2'}}>{isLogin ? 'Need account? Register' : 'Have account? Login'}</button>
        </div>
    );
};
export default Auth;