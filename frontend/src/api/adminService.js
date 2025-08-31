import api from "./axios"

export const getAdmins = () => {
    console.log("fetching", api.defaults.baseURL + "/admin/getAll");
    return api.get("/admin/getAll");
}

export const getAdmin = (id) => api.get(`/admin/read/${id}`);

export const createAdmin = (data) => api.post("/admin/create", data);

export const updateAdmin = (data) => api.put("/admin/update",data);

export const deleteAdmin = (id) => api.delete(`/admin/delete/${id}`);