import {Provider} from "./provider";

export interface Service {
    id: number,
    name: string,
    icon: string,
    area: string,
    price: number,
    color: string,
    description: string, 
    hasExtras: boolean,
    provider: Provider,
    category: string,
    rate: number,
    number_reviews: number
}