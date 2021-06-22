import {Client} from './Client';
import {ProviderService} from './ProviderService';
import {BusinessService} from './BusinessService';
import {ServiceStatus} from './ServiceStatus';

export interface ServiceContract {
    id?: number
    date?: Date,
    providerService?: ProviderService,
    client?: Client,
    businessService?: BusinessService,
    status?: ServiceStatus,
    review?: number
  }