import axios from "axios"
import { ApiBaseUrl } from "../models/Constants"
import { User } from "../models/User"

export class Service {
    loginUser = (username: string, password: string): Promise<User> => {
        return axios.post(`${ApiBaseUrl}/users/login`, {
            username,
            password
        })
        .then(res => res.data)
        .then(u => u.data)
        .catch(e => console.error(e))
    }

    registerUser = (username: string, password: string): Promise<User> => {
        return axios.post(`${ApiBaseUrl}/users/register`, {
            username,
            password
        })
        .then(res => res.data)
        .then(u => u.data)
        .catch(e => console.error(e))
    }
}