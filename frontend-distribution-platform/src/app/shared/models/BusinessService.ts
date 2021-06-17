import { ServiceType } from "./ServiceType";
import {Business} from "./Business"; 

export interface BusinessService{

    id?: number,
    business?: Business,
    service?: ServiceType,
    price?: number

}