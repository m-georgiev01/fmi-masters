import { Channel } from "./Channel";
import { User } from "./User";

export interface Message {
    id: number;
    content: string;
    sender: User;
    channel: Channel;
    timestamp: string;
}