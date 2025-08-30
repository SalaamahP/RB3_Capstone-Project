import api from "./axios"

export const getVenues = () => api.get("/venue/getAll");

export const getVenue = (id) => api.get(`/venue/read/${id}`);

export const createVenue = (data) => api.post("/venue/create", data);


export const updateVenue = (data) => api.put("/venue/update",data);

export const deleteVenue = (id) => api.delete(`/venue/delete/${id}`);

export default{
    getVenues,
    getVenue,
    createVenue,
    updateVenue,
    deleteVenue,
};