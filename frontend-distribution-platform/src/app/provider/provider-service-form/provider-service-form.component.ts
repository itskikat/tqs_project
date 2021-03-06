import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { MENU_ITEMS } from '../../provider-menu';
import { ProviderService } from '../../shared/models/ProviderService';
import { ServiceType } from '../../shared/models/ServiceType';
import { ProviderServiceService } from '../../shared/services/provider-service.service';
import { ServiceTypeServiceService } from '../../shared/services/service-type-service.service';

@Component({
  selector: 'ngx-provider-service-form',
  templateUrl: './provider-service-form.component.html',
  styleUrls: ['./provider-service-form.component.scss']
})
export class ProviderServiceFormComponent implements OnInit {
  
  menu=MENU_ITEMS
  serviceForm: FormGroup;
  
  providerService: ProviderService;
  filteredOptions$: Observable<string[]>;
  serviceTypes: ServiceType[];
  serviceTypes_Names: string[]=[];
  id: number;
  edit: boolean;
  add_disabled: boolean;

  @ViewChild('autoInput') input;

  constructor( 
    private fb: FormBuilder, 
    public router: Router, 
    private route: ActivatedRoute, 
    private providerServiceService: ProviderServiceService,
    private serviceTypeService: ServiceTypeServiceService
  ) { 
    this.id = parseInt(this.route.snapshot.paramMap.get('id'));
    this.edit = this.id>0;
  }

  ngOnInit(): void {
    this.providerService={id:0, provider:{email:""}}
    // If edit
    if (this.edit) {
      // Build form with title disabled
      this.serviceForm = this.fb.group({
        title:[{value:'', disabled:true}, Validators.required],
        description:['', Validators.minLength(3)]
      });
      // Get service data from API and populate form with it
      this.providerServiceService.getProviderService(this.id).subscribe(data=>{
        this.providerService = data;
        this.serviceForm = this.fb.group({
          title:[{value:data.service.name, disabled:true}, Validators.required],
          description:[data.description, Validators.minLength(3)]
        });
      });
    // If adding
    } else {
      // Initialize empty form
      this.serviceForm = this.fb.group({
        title:['', Validators.minLength(3)],
        description:['', Validators.minLength(3)]
      });
      // Get service types from API to form autocomplete
      this.serviceTypeService.getServiceTypes().subscribe(data=>{
        this.serviceTypes= data;
        this.serviceTypes_Names=[];
        this.serviceTypes.forEach(st=> this.serviceTypes_Names.push(st.name));
        this.filteredOptions$ = of(this.serviceTypes_Names);
        this.filteredOptions$ = this.serviceForm.get('title').valueChanges
          .pipe(
            startWith(''),
            map(filterString => this.filter(filterString)),
          );
        }
      );
    }
   
  }

  getFilteredOptions(value: string): Observable<string[]> {
    return of(value).pipe(
      map(filterString => this.filter(filterString)),
    );
  }

  onSelectionChange(): void{
    this.filteredOptions$ = this.getFilteredOptions(this.input.nativeElement.value);
  }

  saveService(): void{
    // If edit, update description and PUT
    if (this.edit) {
      this.providerService.description = this.serviceForm.get('description').value;
      this.providerServiceService.putProviderServices(this.id, this.providerService).subscribe(data=>{
        this.router.navigate(['/provider/services']);
      });
    // If adding, create instance and POST to API
    } else {
      this.providerService.description = this.serviceForm.get('description').value;
      this.providerService.provider.email= localStorage.getItem('email');
      this.providerService.service= this.serviceTypes.find(x => x.name==this.serviceForm.get('title').value);
      this.providerServiceService.postProviderServices(this.providerService).subscribe(data=>{
        this.router.navigate(['/provider/services']);
      });
    }
  }

  private filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    let vals: string[] = this.serviceTypes_Names.filter(optionValue => optionValue.toLowerCase().includes(filterValue)).slice(0,5);
    return vals;
  }
}
