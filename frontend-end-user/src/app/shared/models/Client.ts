import { City } from "./City";
import { User } from "./User";

export interface Client extends User {
    location_city?: City,
    full_name?: string,
    address?: string,
    birthdate?: Date
}