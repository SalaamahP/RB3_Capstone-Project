import api from "./axios"

export const getStudents = () => {
    console.log("fetching", api.defaults.baseURL + "/student/getAll");
    return api.get("/student/getAll");
}

export const getStudent = (id) => api.get(`/student/read/${id}`);

export const createStudent = (data) => api.post("/student/create", data);


export const updateStudent = (data) => api.put("/student/update",data);

export const deleteStudent = (id) => api.delete(`/student/delete/${id}`);
