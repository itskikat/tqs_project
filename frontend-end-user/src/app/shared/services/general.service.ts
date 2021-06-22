import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { apiUrl } from '../../../environments/environment';
import { ServiceContract } from '../models/ServiceContract';
import { ServiceContractPage } from '../models/ServiceContractPage';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class GeneralService {

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }

  // Operations
  getContracts(status: string, sort: string, order: string, page: number) {
    let url = apiUrl + "/contracts?size=3&";
    if (status!="ALL") { url += "status=" + status + "&"; }
    if (sort) { url += "sort=" + sort + "&"; }
    if (order) { url += "order=" + order + "&"; }
    if (page>=0) { url += "page=" + page + "&"; }
    url = url.slice(0, url.length-1);
    return this.http.get<ServiceContractPage>(url, this.authService.getOptions()).toPromise();
  }

  getContract(id: number) {
    return this.http.get<ServiceContract>(apiUrl + "/contracts/" + id, this.authService.getOptions()).toPromise();
  }

  updateContract(id: number, contract: ServiceContract) {
    return this.http.put<ServiceContract>(apiUrl + "/contracts/" + id, contract, this.authService.getOptions()).toPromise();
  }
  
}
