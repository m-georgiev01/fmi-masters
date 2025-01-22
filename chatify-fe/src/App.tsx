import './App.css';
import { Route, Routes, useNavigate } from "react-router";
import { Login } from "./authentication/Login";
import { NonAuthenticatedRoute } from "./guards/NonAuthenticatedRoute";
import { AuthenticatedRoute } from "./guards/AuthenticatedRoute";
import { Layout } from "./layout/Layout";
import React, { useEffect } from 'react';
import { DefaultProps } from './models/DefaultProps';
import { setNavigate } from './navigation';
import { Register } from './authentication/Register';

const App: React.FC<DefaultProps> = ({ state }) => {
  const navigate = useNavigate();

  useEffect(() => {
    setNavigate(navigate);
  }, [navigate]);

  return (
    <Routes>
      <Route path="/login" element={ <NonAuthenticatedRoute state={state}> <Login state={state} /> </NonAuthenticatedRoute> } />
      <Route path="/register" element={<NonAuthenticatedRoute state={state}> <Register state={state}/> </NonAuthenticatedRoute>} />

      <Route path="/" element={ <AuthenticatedRoute state={state}> <Layout state={state} /> </AuthenticatedRoute>} />
      <Route path="*" element={ <AuthenticatedRoute state={state}> </AuthenticatedRoute>} />
    </Routes>
  );
}

export default App;
