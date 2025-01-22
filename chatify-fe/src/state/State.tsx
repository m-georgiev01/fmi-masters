import { User } from "../models/User";
import { action, makeObservable, observable } from "mobx";
import { IState } from "./IState";
import { DefaultString } from "../models/Constants";
import { Service } from "../service/Service";
import { navigateTo } from "../navigation";

class State implements IState {
  private readonly service: Service;

  user: User | null;
  username: string;
  password: string;

  constructor(service: Service) {
    makeObservable(this, {
      user: observable,
      username: observable,
      password: observable,
      saveUsername: action,
      savePassword: action,
      loginUser: action.bound,
      registerUser: action.bound
    })

    this.service = service;
    const userLocalStorage = localStorage.getItem("user");
    this.user = userLocalStorage ? JSON.parse(userLocalStorage) as User : null;
    this.username = DefaultString;
    this.password = DefaultString;
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
    localStorage.setItem("user", JSON.stringify(userApi));
    navigateTo("/")
  }

  logoutUser(){
    localStorage.removeItem("user");
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
    localStorage.setItem("user", JSON.stringify(userApi));
    navigateTo("/")
  }
}

const service = new Service();
export const state = new State(service);  