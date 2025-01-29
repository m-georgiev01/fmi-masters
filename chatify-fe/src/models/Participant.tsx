import { Channel } from "./Channel";
import { Role } from "./Role";
import { User } from "./User";

export interface Participant{
    id: number;
    channel: Channel;
    user: User;
    role: Role;
}