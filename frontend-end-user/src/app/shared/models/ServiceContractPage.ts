import {ServiceContract} from './ServiceContract';

export interface ServiceContractPage{
    data: ServiceContract[],
    currentPage: number,
    totalItems: number,
    totalPages: number
}