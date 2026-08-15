import axios from 'axios';

const API_BASE = 'http://localhost:8080/api/assets';

export const getAllAssets = () => axios.get(API_BASE);

export const getAssetById = (id) => axios.get(`${API_BASE}/${id}`);

export const createAsset = (asset) => axios.post(API_BASE, asset);

export const updateAsset = (id, asset) => axios.put(`${API_BASE}/${id}`, asset);

export const deleteAsset = (id) => axios.delete(`${API_BASE}/${id}`);

export const getDashboardSummary = () => axios.get(`${API_BASE}/dashboard/summary`);