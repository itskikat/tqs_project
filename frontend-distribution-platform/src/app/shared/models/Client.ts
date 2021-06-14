import { User } from "./User";

export interface Client extends User {

    full_name: string,
    address: string,
    birthdate: Date
}