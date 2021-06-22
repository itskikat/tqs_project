import { User } from "./User";

export interface Provider extends User{
    nif?: String,
    birthdate?: Date
}