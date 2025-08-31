import api from "./axios"

export const getOrganizers = () => {
    console.log("fetching", api.defaults.baseURL + "/organizer/getAll");
    return api.get("/organizer/getAll");
}

export const getOrganizer = (id) => api.get(`/organizer/read/${id}`);

export const createOrganizer = (data) => api.post("/organizer/create", data);

export const updateOrganizer = (data) => api.put("/organizer/update",data);

export const deleteOrganizer = (id) => api.delete(`/organizer/delete/${id}`);