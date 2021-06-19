import {ProviderService} from './ProviderService';

interface History{
    date: Date,
    value: number
}

export interface ProviderStatistics{
    TOTAL_PROFIT?: number,
    TOTAL_FINISHED?: number,
    PROFIT_SERVICE?: ProviderService,
    CONTRACTS_SERVICE?: ProviderService,
    PROFIT_HISTORY?: History,
    CONTRACTS_HISTORY?: History
}