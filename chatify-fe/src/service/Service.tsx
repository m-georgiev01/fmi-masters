import axios from "axios"
import { ApiBaseUrl } from "../models/Constants"
import { User } from "../models/User"
import { Channel } from "../models/Channel"
import { Message } from "../models/Message"
import { Participant } from "../models/Participant"

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

    fetchGroupChannels = (userId: number): Promise<Channel[]> => {
        return axios.get(`${ApiBaseUrl}/channels?userId=${userId}`)
        .then(res => res.data)
        .then(c => c.data);
    }

    fetchDms = (userId: number): Promise<Channel[]> => {
        return axios.get(`${ApiBaseUrl}/channels/dm?userId=${userId}`)
        .then(res => res.data)
        .then(c => c.data);
    }

    fetchMessages = (channelId: number): Promise<Message[]> => {
        return axios.get(`${ApiBaseUrl}/channels/${channelId}/messages`)
        .then(res => res.data)
        .then(m => m.data);
    }

    fetchChannelMembers = (channelId: number): Promise<Participant[]> => {
        return axios.get(`${ApiBaseUrl}/channels/${channelId}/members`)
        .then(res => res.data)
        .then(p => p.data)
        .catch(e => console.error(e));
    }

    removeFromChannel = (channelId: number, initiatorId: number, targetId: number): Promise<number> => {
        return axios.delete(`${ApiBaseUrl}/channels/${channelId}/members`, {
            data: {
                initiatorId,
                targetId
            }
        })
        .then(res => res.status)
        .catch(e => {
            console.error(e);
            return e.status
        });
    }

    promoteToAdmin = (initiatorId: number, targetId: number, channelId:number): Promise<Participant> => {
        return axios.put(`${ApiBaseUrl}/channels/roles/promote`, {
            initiatorId,
            targetId,
            channelId
        })
        .then(res => res.data)
        .then(u => u.data)
        .catch(e => console.error(e));
    }

    saveChangedChannelName = (channelId: number, userId: number, channelName: string): Promise<Channel> => {
        return axios.put(`${ApiBaseUrl}/channels`, {
            channelId,
            userId,
            channelName
        })
        .then(res => res.data)
        .then(c => c.data);
    }

    sendMessage = (channelId: number, senderId: number, content: string): Promise<Message> => {
        return axios.post(`${ApiBaseUrl}/channels/${channelId}/messages`,
            {
                senderId,
                content
            })
            .then(res => res.data)
            .then(m => m.data);
    }

    searchForUsers = (username: string): Promise<User[]> => {
        return axios.get(`${ApiBaseUrl}/users/search?username=${username}`)
            .then(res => res.data)
            .then(u => u.data)
            .catch(e => console.error(e));
    }

    addFriend = (senderId: number, receiverId: number): Promise<Channel> => {
        return axios.post(`${ApiBaseUrl}/users/friends/add`, {
            senderId,
            receiverId
        })
        .then(res => res.data)
        .then(d => d.data);
    }

    createChannel = (userId: number, channelName: string): Promise<Channel> => {
        return axios.post(`${ApiBaseUrl}/channels`,
            {
                userId,
                channelName
            })
            .then(res => res.data)
            .then(c => c.data);
    }

    deleteChannel = (channelId: number, userId: number): Promise<number> => {
        return axios.delete(`${ApiBaseUrl}/channels`, {
            data: {
                channelId,
                userId
            }
        })
        .then(res => res.status)
        .catch(e => {
            console.error(e);
            return e.status
        });
    }

    addNewMember = (channelId: number, initiatorId: number, newUserId: number): Promise<Participant> => {
        return axios.post(`${ApiBaseUrl}/channels/${channelId}/members`,
            {
                initiatorId,
                newUserId
            })
            .then(res => res.data)
            .then(c => c.data);
    }
}