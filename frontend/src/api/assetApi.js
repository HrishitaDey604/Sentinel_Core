import axiosInstance from './axiosInstance';

const API_BASE = 'http://localhost:8080/api/assets';

export const getAllAssets = () => axiosInstance.get(API_BASE);

export const getAssetById = (id) => axiosInstance.get(`${API_BASE}/${id}`);

export const createAsset = (asset) => axiosInstance.post(API_BASE, asset);

export const updateAsset = (id, asset) => axiosInstance.put(`${API_BASE}/${id}`, asset);

export const deleteAsset = (id) => axiosInstance.delete(`${API_BASE}/${id}`);

export const getDashboardSummary = () => axiosInstance.get(`${API_BASE}/dashboard/summary`);