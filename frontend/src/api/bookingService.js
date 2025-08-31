// frontend/src/api/bookingService.js
import api from "./api";

// List
export async function listBookings() {
    const res = await api.get("/bookings");
    return res.data;
}

// Read
export async function getBooking(id) {
    const res = await api.get(`/bookings/${id}`);
    return res.data;
}

// Create
export async function createBooking(payload) {
    const res = await api.post("/bookings", payload);
    return res.data;
}

// Update
export async function updateBooking(id, payload) {
    const res = await api.put(`/bookings/${id}`, payload);
    return res.data;
}

// Delete
export async function deleteBooking(id) {
    await api.delete(`/bookings/${id}`);
}

