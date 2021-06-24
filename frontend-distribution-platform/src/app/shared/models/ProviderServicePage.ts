import {ProviderService} from './ProviderService';

export interface ProviderServicePage{
    data: ProviderService[],
    currentPage: number,
    totalItems: number,
    totalPages: number
}