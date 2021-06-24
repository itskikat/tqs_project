import { User } from "./User";


export interface District{
    name?: string
}

export interface City{
    name?: string,
    district?: number 
}

export interface Provider extends User{
    nif?: String,
    birthdate?: Date
    category?: String
    districts?: District[]
    cities?:City[]
}