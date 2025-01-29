import { User } from "../models/User";
import { action, makeObservable, observable, runInAction } from "mobx";
import { IState } from "./IState";
import { DefaultString, NoContent } from "../models/Constants";
import { Service } from "../service/Service";
import { navigateTo } from "../navigation";
import { Channel } from "../models/Channel";
import { Message } from "../models/Message";
import { Participant } from "../models/Participant";

class State implements IState {
  private readonly service: Service;

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

  constructor(service: Service) {
    makeObservable(this, {
      user: observable,
      username: observable,
      password: observable,
      channels: observable,
      dms: observable,
      selectedChannel: observable,
      messages: observable,
      channelMembers: observable,
      isEditingChannel: observable,
      editedChannelName: observable,
      message: observable,
      userInput: observable,
      usersSearch:observable,
      isCreateModalOpen: observable,
      createChannelInput: observable,
      membersSearch: observable,
      membersInput: observable,

      saveUsername: action,
      savePassword: action,
      loginUser: action.bound,
      logoutUser: action.bound,
      registerUser: action.bound,
      setSelectedChannel: action.bound,
      getMessages: action.bound,
      getChannelMembers: action.bound,
      removeMember: action.bound,
      promoteToAdmin: action.bound,
      changeIsEditingChannel: action.bound,
      changeChannelName: action.bound,
      saveChangedChannelName: action.bound,
      sendMessage: action.bound,
      setMessage: action.bound,
      setUserSearchInput: action.bound,
      clearUsersSearch: action.bound,
      searchUsers: action.bound,
      addFriend: action.bound,
      changeIsCreateModalOpen: action.bound,
      setCreateChannelInput: action.bound,
      createChannel: action.bound,
      deleteChannel: action.bound,
      setMembersSearchInput: action.bound,
      clearMembersSearch: action.bound,
      searchMembers: action.bound,
      addMember: action.bound,

      initialLoad: action.bound
    })

    this.service = service;
    const userLocalStorage = localStorage.getItem("user");
    this.user = userLocalStorage ? JSON.parse(userLocalStorage) as User : null;
    this.username = DefaultString;
    this.password = DefaultString;
    this.channels = [];
    this.dms = [];
    this.selectedChannel = null;
    this.messages = [];
    this.channelMembers = [];
    this.isEditingChannel = false;
    this.editedChannelName = DefaultString;
    this.message = DefaultString;
    this.userInput = DefaultString;
    this.usersSearch = null;
    this.isCreateModalOpen = false;
    this.createChannelInput = DefaultString;
    this.membersSearch = null;
    this.membersInput = DefaultString;

    this.initialLoad();
  }

  saveUsername(usernameInput: string) {
    this.username = usernameInput;
  }

  savePassword(passwordInput: string) {
    this.password = passwordInput;
  }

  async loginUser() {
    if (this.username == DefaultString || this.password == DefaultString) {
      console.error("Fill the username and passords fields!")
      return;
    }

    if (this.user) {
      console.error("User already logged!")
      return; 
    }

    const userApi = await this.service.loginUser(this.username, this.password);
    if (!userApi) {
      return;
    }

    this.user = userApi;
    this.username = DefaultString;
    this.password = DefaultString;
    localStorage.setItem("user", JSON.stringify(userApi));
    this.initialLoad();
    navigateTo("/")
  }

  logoutUser(){
    this.user = null;
    this.selectedChannel = null;
    localStorage.removeItem("user");
    navigateTo("/login")
  }

  async registerUser(){
    if (this.username == DefaultString || this.password == DefaultString) {
      console.error("Fill the username and passords fields!")
      return;
    }

    if (this.user) {
      console.error("User already logged!")
      return; 
    }

    const userApi = await this.service.registerUser(this.username, this.password);
    if (!userApi) {
      return;
    }

    this.user = userApi;
    this.username = DefaultString;
    this.password = DefaultString;
    this.channels = [];
    localStorage.setItem("user", JSON.stringify(userApi));
    navigateTo("/")
  }

  async setSelectedChannel(channel: Channel) {
    this.selectedChannel = channel;
    await this.getMessages();
    await this.getChannelMembers();
    
    runInAction(() => {
      this.isEditingChannel = false;
      this.editedChannelName = DefaultString;
    })
  }

  async getMessages() {
    const msgs = await this.service.fetchMessages(this.selectedChannel?.id ?? -1);
    runInAction(() => {
      this.messages = msgs;
    })
  }

  async getChannelMembers(){
    const channelMembers = await this.service.fetchChannelMembers(this.selectedChannel?.id ?? -1);
    runInAction(() => {
      this.channelMembers = channelMembers;
    })
  }

  async removeMember(targetId: number) {
    if (this.selectedChannel && this.user) {
      const statusCode = await this.service.removeFromChannel(this.selectedChannel.id, this.user.id, targetId)

      if (statusCode === NoContent) {
        runInAction(() => {
          this.channelMembers = this.channelMembers.filter(c => c.user.id !== targetId);
        })
      }
    }
  }

  async promoteToAdmin(targetId: number) {
    if (this.selectedChannel && this.user) {
      const updated = await this.service.promoteToAdmin(this.user.id, targetId, this.selectedChannel.id);

      runInAction(() => {
        const index = this.channelMembers.findIndex(m => m.id === updated.id);
        if (index !== -1) {
          this.channelMembers.splice(index, 1, updated);
        }
      })
    }
  }

  changeIsEditingChannel(value: boolean){
    this.isEditingChannel = value;

    if(value) this.editedChannelName = this.selectedChannel?.name ?? DefaultString;
  }

  changeChannelName(name: string){
    this.editedChannelName = name;
  }

  async saveChangedChannelName() {
    if(this.selectedChannel && this.user){
      const changedChannel = await this.service.saveChangedChannelName(this.selectedChannel.id, this.user.id, this.editedChannelName);

      runInAction(() => {
        this.selectedChannel = changedChannel;
      
        const index = this.channels.findIndex(c => c.id === changedChannel.id);
        if(index !== -1) this.channels.splice(index, 1, changedChannel);

        this.isEditingChannel = false;
      })
    }
  }

  setMessage(value: string){
    this.message = value;
  }

  async sendMessage(){
    if(this.selectedChannel && this.user && this.message !== DefaultString){
      var msg = await this.service.sendMessage(this.selectedChannel.id, this.user.id, this.message);

      this.messages.push(msg);
      this.message = DefaultString
    }
  }

  setUserSearchInput(value: string) {
    this.userInput = value;
  }

  clearUsersSearch(){
    this.usersSearch = null;
  }

  async searchUsers() {
    if (this.userInput !== DefaultString) {
      var users = await this.service.searchForUsers(this.userInput);
      users = users.filter(u => u.id !== this.user?.id);
      
      runInAction(() => {
        this.userInput = DefaultString;
        this.usersSearch = users;
      })
    }
  }

  async addFriend(receiverId: number){
    if(this.user){
      const dm = await this.service.addFriend(this.user.id, receiverId);
      
      runInAction(() => {
        this.dms.push(dm);
        this.userInput = DefaultString;
        this.usersSearch = null;
      })
    }
  }

  changeIsCreateModalOpen(value: boolean) {
    this.isCreateModalOpen = value

    if(!value) this.createChannelInput = DefaultString;
  }

  setCreateChannelInput(value: string){
    this.createChannelInput = value;
  }

  async createChannel() {
    if(this.user && this.createChannelInput !== DefaultString){
      const channel = await this.service.createChannel(this.user.id, this.createChannelInput);

      runInAction(() => {
        this.channels.push(channel);
      })

      this.changeIsCreateModalOpen(false);
    }
    
  }

  async deleteChannel() {
    if(this.selectedChannel && this.user){
      const statusCode = await this.service.deleteChannel(this.selectedChannel.id, this.user.id);
      
      if (statusCode === NoContent) {
        runInAction(() => {
          this.channels = this.channels.filter(c => c.id !== this.selectedChannel?.id);
          this.selectedChannel = null;
        })
      }
    }
  }

  setMembersSearchInput(value: string){
    this.membersInput = value;
  }

  clearMembersSearch(){
    this.membersSearch = null;
  }

  async searchMembers() {
    if (this.membersInput !== DefaultString) {
      var users = await this.service.searchForUsers(this.membersInput);

      const participantIds = this.channelMembers.map(participant => participant.user.id);
      users = users.filter(u => !participantIds.includes(u.id));
      
      runInAction(() => {
        this.membersInput = DefaultString;
        this.membersSearch = users;
      })
    }
  }

  async addMember(targetId: number){
    if(this.user && this.selectedChannel){
      const participant = await this.service.addNewMember(this.selectedChannel.id, this.user.id, targetId);
      
      runInAction(() => {
        this.channelMembers.push(participant);
        this.membersInput = DefaultString;
        this.membersSearch = null;
      })
    }
  }

  async initialLoad(){
    if (this.user) {
      try {
        const groupChannels = await this.service.fetchGroupChannels(this.user.id)
        const dms = await this.service.fetchDms(this.user.id);

        runInAction(() => {
          this.channels = groupChannels;
          this.dms = dms;
        }) 
      } catch (e) {
        console.error(e);
      }
    }
  }
}

const service = new Service();
export const state = new State(service);