import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {AuthService} from './auth.service';
import { Observable } from 'rxjs';
import {ProviderService} from '../models/ProviderService';
import {ProviderServicePage} from '../models/ProviderServicePage';
import { apiUrl } from '../../../environments/environment';
import {ProviderStatistics} from '../models/ProviderStatistics';
import { District, Provider , City} from '../models/Provider';

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

  getProviderStatistics(start: string, end:string): Observable<ProviderStatistics>{
    let url = apiUrl + "/provider/statistics?start=" + start + "&end=" + end;
    return this.http.get<ProviderStatistics>(url,this.authservice.getOptions());
  }

  registProvider(p:Provider): Observable<Provider>{
    let url = apiUrl + "/provider/";
    return this.http.post<Provider>(url,p,this.authservice.getOptions() )
  }

  getBusiness(p: string): Observable<Provider>{
    let url = apiUrl + "/provider/"+ p;
    return this.http.get<Provider>(url,this.authservice.getOptions() )
  }

  putBusiness(p:Provider): Observable<Provider>{
    let url = apiUrl + "/provider/"+p.email;
    return this.http.put<Provider>(url,p,this.authservice.getOptions() )
  }

  getdistricts(): Observable<District[]>{
    let url = apiUrl + "/districts";
    return this.http.get<District[]>(url,this.authservice.getOptions() )
  }

  getcities(): Observable<City[]>{
    let url = apiUrl + "/cities";
    return this.http.get<City[]>(url,this.authservice.getOptions() )
  }

  getProvider(p: string): Observable<Provider>{
    let url = apiUrl + "/provider/"+ p;
    return this.http.get<Provider>(url,this.authservice.getOptions() )
  }

  putProvider(email: String, p: Provider): Observable<Provider>{
    let url = apiUrl + "/provider/" + email;
    return this.http.put<Provider>(url, p, this.authservice.getOptions())
  }
}
