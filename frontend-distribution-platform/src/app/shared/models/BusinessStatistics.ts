import { ServiceType } from './ServiceType';

interface History{
    date: string,
    value: number
}

export interface BusinessStatistics{
    PROFIT?: number,
    TOTAL_CONTRACTS?: number,
    MOST_REQUESTED_SERVICETYPE?: ServiceType,
    PROFIT_HISTORY?: Map<string, number>
    end_date?: Date
    start_date?: Date
}
