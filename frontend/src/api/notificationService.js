// frontend/src/api/notificationService.js
import api from "./api";

// List
export async function listNotifications() {
    const res = await api.get("/notifications");
    return res.data;
}

// Read
export async function getNotification(id) {
    const res = await api.get(`/notifications/${id}`);
    return res.data;
}

// Create
export async function createNotification(payload) {
    const res = await api.post("/notifications", payload);
    return res.data;
}

// Update
export async function updateNotification(id, payload) {
    const res = await api.put(`/notifications/${id}`, payload);
    return res.data;
}

// Delete
export async function deleteNotification(id) {
    await api.delete(`/notifications/${id}`);
}
