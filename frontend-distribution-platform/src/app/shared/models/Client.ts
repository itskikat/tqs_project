import { User } from "./User";

export interface Client extends User {

    name: string,
    address: string,
    birthdate: Date
}