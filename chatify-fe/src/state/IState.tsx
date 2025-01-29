import { Channel } from "../models/Channel";
import { Message } from "../models/Message";
import { Participant } from "../models/Participant";
import { User } from "../models/User";

export interface IState {
  user: User | null;
  username: string;
  password: string;
  channels: Channel[];
  dms: Channel[];
  selectedChannel: Channel | null;
  messages: Message[];
  channelMembers: Participant[];
  isEditingChannel: boolean;
  editedChannelName: string;
  message: string;
  userInput: string;
  usersSearch: User[] | null;
  isCreateModalOpen: boolean;
  createChannelInput: string;
  membersSearch: User[] | null;
  membersInput: string;

  saveUsername(usernameInput: string): void
  savePassword(passwordInput: string): void
  loginUser(): void
  logoutUser(): void
  registerUser(): void
  setSelectedChannel(channel: Channel): void
  getMessages(): void
  getChannelMembers(): void
  removeMember(targetId: number): void
  promoteToAdmin(targetId: number): void
  changeIsEditingChannel(value: boolean): void
  changeChannelName(name: string): void
  saveChangedChannelName(): void
  setMessage(value: string): void
  sendMessage(): void
  setUserSearchInput(value: string): void
  clearUsersSearch(): void
  searchUsers(): void
  addFriend(receiverId: number): void
  changeIsCreateModalOpen(value: boolean): void
  setCreateChannelInput(value: string): void
  createChannel(): void
  deleteChannel(): void
  setMembersSearchInput(value: string): void
  clearMembersSearch(): void
  searchMembers(): void
  addMember(targetId: number): void

  initialLoad(): void
}
