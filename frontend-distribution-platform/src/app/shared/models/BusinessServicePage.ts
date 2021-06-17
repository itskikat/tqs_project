import {BusinessService} from './BusinessService';

export interface BusinessServicePage{
    data: BusinessService[],
    currentPage: number,
    totalItems: number,
    totalPages: number
}