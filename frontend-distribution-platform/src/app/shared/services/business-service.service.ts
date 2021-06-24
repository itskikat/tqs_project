import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient } from '@angular/common/http';
import {BusinessService} from '../models/BusinessService';
import {BusinessStatistics } from '../models/BusinessStatistics';
import {BusinessServicePage} from '../models/BusinessServicePage';
import {Business} from '../models/Business';
import { apiUrl } from '../../../environments/environment';
import {AuthService} from './auth.service';

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

  postBusinnessServices( bs: BusinessService): Observable<BusinessService> {
    let url = apiUrl + "/businesses/services";
    return this.http.post<BusinessService>(url,bs,this.authservice.getOptions());
  }

  deleteBusinessServices(id:number): any {
    let url = apiUrl + "/businesses/services/delete/" + id ;
    let headers = this.authservice.getOptions();
    headers['responseType'] = 'text';
    return this.http.delete(url, headers);
  }

  getBusinessStatistics(start: string, end:string): Observable<BusinessStatistics>{
    let url = apiUrl + "/businesses/statistics?start=" + start + "&end=" + end;
    return this.http.get<BusinessStatistics>(url,this.authservice.getOptions());
  }

  registBusiness(b:Business): Observable<Business>{
    let url = apiUrl + "/business/";
    return this.http.post<Business>(url,b,this.authservice.getOptions() )
  }

  getBusiness(b: string): Observable<Business>{
    let url = apiUrl + "/business/"+ b;
    return this.http.get<Business>(url,this.authservice.getOptions() )
  }

  putBusiness(b:Business): Observable<Business>{
    let url = apiUrl + "/business/"+b.email;
    return this.http.put<Business>(url,b,this.authservice.getOptions() )
  }

  generateToken(): Observable<any>{
    let url = apiUrl + "/business/token";
    return this.http.get<any>(url,this.authservice.getOptions() )
  }

}
