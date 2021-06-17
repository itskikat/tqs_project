import { Injectable } from '@angular/core';
import {AuthService} from './auth.service';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient } from '@angular/common/http';
import { apiUrl } from '../../../environments/environment';
import { ServiceType } from '../models/ServiceType';



@Injectable({
  providedIn: 'root'
})
export class ServiceTypeServiceService {

  constructor(private http: HttpClient, private authservice: AuthService) { }

  getServiceTypes(name?: string): Observable<ServiceType[]> {
    let url;
    if(name){
      url = apiUrl + "/servicetypes?name="+name;
    }
    else{
      url = apiUrl + "/servicetypes";
    }
    return this.http.get<ServiceType[]>(url,this.authservice.getOptions());
  }

  postServiceType(st: ServiceType): Observable<ServiceType> {
    let url = apiUrl + "/servicetypes";
    return this.http.post<ServiceType>(url,st,this.authservice.getOptions());
  }
}
