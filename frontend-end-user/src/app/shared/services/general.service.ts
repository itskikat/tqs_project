import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { apiUrl } from '../../../environments/environment';
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
  
}
