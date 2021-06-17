import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient } from '@angular/common/http';
import {ServiceContract} from '../models/ServiceContract';
import {ServiceContractPage} from '../models/ServiceContractPage';
import { apiUrl } from '../../../environments/environment';
import {AuthService} from './auth.service';

@Injectable({
  providedIn: 'root'
})

export class ServiceContractService {

  constructor(private http: HttpClient, private authservice: AuthService) { }

  getServiceContract(id: number, role: string): Observable<ServiceContract> {
    let url;
    if (role =="p"){
      url = apiUrl + "/provider/contracts/" + id + "/";
    }
    else{
      url = apiUrl + "/businesses/contracts/" + id + "/";
    }
    return this.http.get<ServiceContract>(url,this.authservice.getOptions());
  }

  getServiceContracts(role:string, pageurl?: string): Observable<ServiceContractPage> {
    let url;
    if (role =="p"){
      url = apiUrl + "/provider/contracts";
    }
    else{
      url = apiUrl + "/businesses/contracts";
    }
    if (pageurl) {
      return this.http.get<ServiceContractPage>(url+pageurl,this.authservice.getOptions());
    }
    return this.http.get<ServiceContractPage>(url,this.authservice.getOptions());
  }

  putServiceContract(id: number, sc: ServiceContract): Observable<ServiceContract> {
    let url = apiUrl + "/provider/contracts/" + id + "/";
    return this.http.put<ServiceContract>(url,sc,this.authservice.getOptions());
  }

}
