import {Provider} from './Provider';
import { ServiceType } from './ServiceType';

export interface ProviderService{
    id: number;
    provider: Provider;
    service: ServiceType;
    description: String;
}