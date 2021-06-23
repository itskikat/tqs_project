import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ValidationErrors, ValidatorFn, AbstractControl } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import {Provider, District, City} from '../shared/models/Provider';
import {ProviderServiceService} from '../shared/services/provider-service.service';


@Component({
  selector: 'ngx-register-provider',
  templateUrl: './register-provider.component.html',
  styleUrls: ['./register-provider.component.scss']
})
export class RegisterProviderComponent implements OnInit {

  personalDataForm: FormGroup;
  providerAccountForm: FormGroup;
  filteredOptions$: Observable<string[]>;
  filteredOptionsCity$: Observable<string[]>;
  districts: District[]=[{name:'Init'}];
  cities: City[]=[{name:'Init'}];
  districts_Names: string[]=[];
  cities_Names: string[]=[];

  selected_districts: string[]=[ ];
  selected_cities: string[] =[];

  minDate: Date;

  constructor(private fb: FormBuilder,public router: Router, private providerService: ProviderServiceService  ) { }

  ngOnInit(): void {
    this.minDate = new Date();
    this.minDate.setFullYear(this.minDate.getFullYear() - 18);  
    this.personalDataForm = this.fb.group({
      firstName: ['', Validators.minLength(3)],
      lastName: ['', Validators.minLength(3)],
      bdate: [''],
      vat:['', [Validators.pattern('[0-9]*')]],
      category:['', Validators.minLength(3)],
      district:[''],
      city:['']
    });
    this.providerAccountForm = this.fb.group({
      email: ['', Validators.email],
      pass: ['', Validators.minLength(3)],
      newpass: ['',[Validators.minLength(3), this.checkEqual()]],
    });

    this.providerService.getdistricts().subscribe(data=>{
      this.districts= data;
      this.districts_Names=[];
      this.districts.forEach(st=> this.districts_Names.push(st.name));
      this.filteredOptions$ = of(this.districts_Names);
      this.filteredOptions$ = this.personalDataForm.get('district').valueChanges
        .pipe(
          startWith(''),
          map(filterString => this.filter(filterString)),
        );
  
      }
    );
    this.providerService.getcities().subscribe(data=>{
      this.cities= data;
      this.cities_Names=[];
      this.cities.forEach(st=> this.cities_Names.push(st.name));
      this.filteredOptionsCity$ = of(this.cities_Names);
      this.filteredOptionsCity$ = this.personalDataForm.get('city').valueChanges
        .pipe(
          startWith(''),
          map(filterString => this.filter_cities(filterString)),
        );
  
      }
    );
  }

  checkEqual(): ValidatorFn{
    return (control:AbstractControl) : ValidationErrors | null => {
      const value = control.value;
      if (!value) {
        return null;
      }
      if(this.providerAccountForm.get("pass").value!=this.providerAccountForm.get("newpass").value){
        return null;
      }

    }
    
  }
  
  

  finishRegister(){

    let provider: Provider={email:''};
    provider.email= this.providerAccountForm.value.email;
    provider.password= this.providerAccountForm.get("pass").value;
    provider.birthdate= this.personalDataForm.get("bdate").value;
    provider.full_name= this.personalDataForm.get("firstName").value + this.personalDataForm.get("lastName").value ;
    provider.nif= this.personalDataForm.get("vat").value;
    let cities_final: City[]=[]
    for(var i=0; i< this.selected_cities.length; i++){
      cities_final.push(this.cities.filter(x=>x.name==this.selected_cities[i])[0]);
    }
    let districts_final: District[]=[]
    for(var i=0; i< this.selected_districts.length; i++){
      districts_final.push(this.districts.filter(x=>x.name==this.selected_districts[i])[0]);
    }
    provider.cities= cities_final
    provider.districts= districts_final
    this.providerService.registProvider(provider).subscribe(
      data =>{
        if(data == null){
          location.reload();
          alert("Email already exits! Please fill the forms again!")
        }
        else{
          this.router.navigate(['']);
        }
        
      }
    );

  }

  onDistrictChange(){
    if(this.personalDataForm.get("district").value != ""){
      this.selected_districts.push(this.personalDataForm.get("district").value);
    }
    
  }

  onCityChange(){
    if(this.personalDataForm.get("city").value != ""){
      this.selected_cities.push(this.personalDataForm.get("city").value);
    }
  }

  deleteDistrict(name){
    this.selected_districts.splice(this.selected_districts.indexOf(name),1);
  }

  deleteCity(name){
    this.selected_cities.splice(this.selected_cities.indexOf(name),1);
  }

  private  filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    let vals: string[] = this.districts_Names.filter(optionValue => optionValue.toLowerCase().includes(filterValue)).slice(0,5);
    return vals;

  }

  private  filter_cities(value: string): string[] {
    const filterValue = value.toLowerCase();
    let vals: string[] = this.cities_Names.filter(optionValue => optionValue.toLowerCase().includes(filterValue)).slice(0,5);
    return vals;

  }

}


