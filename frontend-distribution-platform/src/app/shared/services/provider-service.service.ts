import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {AuthService} from './auth.service';
import { Observable } from 'rxjs';
import {ProviderService} from '../models/ProviderService';
import {ProviderServicePage} from '../models/ProviderServicePage';
import { apiUrl } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProviderServiceService {

  constructor(private http: HttpClient, private authservice: AuthService) { }

  getProviderService(id: number): Observable<ProviderService> {
    let url= apiUrl + "/provider/services/" + id ;

    return this.http.get<ProviderService>(url,this.authservice.getOptions());
  }

  getProviderServices(pageurl?: string): Observable<ProviderServicePage> {
    let url= apiUrl + "/provider/services";
    if (pageurl) {
      return this.http.get<ProviderServicePage>(url+pageurl,this.authservice.getOptions());
    }
    return this.http.get<ProviderServicePage>(url,this.authservice.getOptions());
  }

  putProviderServices(id: number, bs: ProviderService): Observable<ProviderService> {
    let url = apiUrl + "/provider/services/" + id;
    return this.http.put<ProviderService>(url,bs,this.authservice.getOptions());
  }

  postProviderServices( bs: ProviderService): Observable<ProviderService> {
    let url = apiUrl + "/provider/services";
    return this.http.post<ProviderService>(url,bs,this.authservice.getOptions());
  }

  deleteProviderServices(id:number): any {
    let url = apiUrl + "/provider/services/delete/" + id ;
    let headers = this.authservice.getOptions();
    headers['responseType'] = 'text';
    return this.http.delete(url, headers);
  }
}
