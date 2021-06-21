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
  getContracts() {
    return this.http.get<ServiceContractPage>(apiUrl + "/contracts", this.authService.getOptions()).toPromise();
  }
  
}
