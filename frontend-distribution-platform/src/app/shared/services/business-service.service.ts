import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient } from '@angular/common/http';
import {BusinessService} from '../models/BusinessService';
import {BusinessServicePage} from '../models/BusinessServicePage';
import { apiUrl } from '../../../environments/environment';
import {AuthService} from './auth.service';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BusinessServiceService {

  constructor(private http: HttpClient, private authservice: AuthService) { }

  getBusinnessService(id: number): Observable<BusinessService> {
    let url= apiUrl + "/businesses/services/" + id ;

    return this.http.get<BusinessService>(url,this.authservice.getOptions());
  }

  getBusinnessServices(pageurl?: string): Observable<BusinessServicePage> {
    let url= apiUrl + "/businesses/services";
    if (pageurl) {
      return this.http.get<BusinessServicePage>(url+pageurl,this.authservice.getOptions());
    }
    return this.http.get<BusinessServicePage>(url,this.authservice.getOptions());
  }

  putBusinnessServices(id: number, bs: BusinessService): Observable<BusinessService> {
    let url = apiUrl + "/businesses/services/" + id;
    return this.http.put<BusinessService>(url,bs,this.authservice.getOptions());
  }

  deleteBusinessServices(id:number): any {
    let url = apiUrl + "/businesses/services/delete/" + id ;
    let headers = this.authservice.getOptions();
    headers['responseType'] = 'text';
    return this.http.delete(url, headers);
  }

}
