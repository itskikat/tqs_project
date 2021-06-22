import {ProviderService} from './ProviderService';

interface History{
    date: string,
    value: number
}

export interface ProviderStatistics{
    TOTAL_PROFIT?: number,
    TOTAL_FINISHED?: number,
    PROFIT_SERVICE?: ProviderService,
    CONTRACTS_SERVICE?: ProviderService,
    PROFIT_HISTORY: Map<string, number>,
    CONTRACTS_HISTORY: Map<string, number>
}