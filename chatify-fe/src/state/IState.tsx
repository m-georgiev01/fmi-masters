import { User } from "../models/User";

export interface IState {
  user: User | null;
  username: string;
  password: string;

  saveUsername(usernameInput: string): void
  savePassword(passwordInput: string): void
  loginUser(): void
  registerUser(): void
}
