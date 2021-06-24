import { Component, OnInit } from '@angular/core';
import { MENU_ITEMS } from '../../provider-menu';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Provider} from '../../shared/models/Provider';
import { ProviderServiceService} from '../../shared/services/provider-service.service';

@Component({
  selector: 'ngx-provider-profile',
  templateUrl: './provider-profile.component.html',
  styleUrls: ['./provider-profile.component.scss']
})
export class ProviderProfileComponent implements OnInit {

  menu=MENU_ITEMS
  minDate: Date;
  personalDataForm: FormGroup;
  formDisabled: boolean;
  provider: Provider;
  bdate: string;
  email: String;

  constructor(private fb: FormBuilder,public router: Router, private providerService: ProviderServiceService) { }

  ngOnInit(): void {
    this.formDisabled=true;
    this.minDate = new Date();

    this.minDate.setFullYear(this.minDate.getFullYear() - 18);  
    this.personalDataForm = this.fb.group({
      firstName: ['', Validators.minLength(3)],
      lastName: ['', Validators.minLength(3)],
      vat:['',Validators.pattern('[0-9]*')],
      category:['']
    });
    this.providerService.getProvider(localStorage.getItem("email")).subscribe(
      data=>{
        console.log(data)
        this.provider=data
        this.personalDataForm.get('firstName').setValue(data.full_name.split(' ')[0]);
        this.personalDataForm.get('lastName').setValue(data.full_name.split(' ')[1]);
        this.bdate = data.birthdate[2] + "/" + data.birthdate[1]+ "/" + data.birthdate[0];
        this.email= data.email;
        this.personalDataForm.get('vat').setValue(data.nif);
        this.personalDataForm.get('category').setValue(data.category);
      }
    );
    this.personalDataForm.disable();
  }

  editProfile(): void{
    this.formDisabled=false;
    this.personalDataForm.enable();
  }

  saveProfile(): void{
    this.personalDataForm.disable();
    this.provider.full_name= this.personalDataForm.get("firstName").value + ' ' +this.personalDataForm.get("lastName").value ;
    this.provider.nif= this.personalDataForm.get("vat").value;
    this.provider.category = this.personalDataForm.get('category').value;
    this.providerService.putProvider(localStorage.getItem("email"), this.provider).subscribe(    );

    this.ngOnInit();
  }
}
