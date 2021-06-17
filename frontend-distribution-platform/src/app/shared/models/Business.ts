import {User} from './User';

export interface Business extends User{
    apikey?: string,
    name?: string,
    address?: string,
    nif?: string
}