import { ThisReceiver } from "@angular/compiler";
import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, ValidationErrors, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { City } from "src/app/shared/models/City";
import { Client } from "src/app/shared/models/Client";
import { District } from "src/app/shared/models/District";
import { AuthService } from "src/app/shared/services/auth.service";
import { GeneralService } from "src/app/shared/services/general.service";

@Component({
  selector: "service-register",
  templateUrl: "./register.component.html",
  host: { 'class': 'w-full flex flex-column flex-wrap' }
})
export class ServiceRegisterComponent implements OnInit {

  // Form
  registerForm: FormGroup;
  maxDate: String;
  birthDate: String;
  invalid: String = "";
  submitted: boolean;
  // Options
  districts: District[];
  districtSelected: number;
  cities: City[];
  citySelected: number;
  client: Client;
  
  // Edit
  edit: boolean;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private generalService: GeneralService,
    public router: Router
  ) {
    // If /profile on edit mode, else on register
    this.edit = this.router.url.split('?')[0]=="/profile";
  }

  ngOnInit(): void {
    this.submitted = false;

    if (!this.edit) {
      this.redirect();
    }

    // Form restrictions
    let d = new Date();
    d.setFullYear(d.getFullYear() - 18);
    this.maxDate = d.getFullYear() + "-0" + d.getMonth() + '-' + d.getDate();

    // Form builder
    this.registerForm = this.fb.group({
      email: [{value: '', disabled: true}, Validators.email],
      password: ['', ],
      full_name: ['', Validators.minLength(3)],
      address: ['', Validators.minLength(3)],
      birthdate: ['',],
      district: ['',],
      location_city: [{ value: '', disabled: true },],
    });

    // Populate select options
    this.generalService.getDistricts().then(data => {
      this.districts = data;
      console.log("DISTRICTS", data);
    });

    // If edit, get data from API
    console.log("EDIT?", this.edit);
    if (this.edit) {
      this.authService.clientLogged().then(d => {
        console.log("GOT CLIENT LOGGEF", d);
        this.client = d;
        this.populateForm(d);
      })
    } else {
      // If registering, password is mandatory
      this.registerForm.controls['password'].setValidators([Validators.minLength(3)]);
      // If registering, enable email (disabled by default)
      this.registerForm.controls['email'].enable();
    }
  }

  // Populate form
  populateForm(d: Client) {
    this.registerForm.controls['email'].patchValue(d['email']);
    this.registerForm.controls['full_name'].patchValue(d['full_name']);
    this.registerForm.controls['address'].patchValue(d['address']);
    this.registerForm.controls['birthdate'].patchValue(d['birthdate']);
    this.registerForm.controls['district'].patchValue(d['location_city']['district']['id']);
    this.registerForm.controls['location_city'].patchValue(d['location_city']['id']);
    this.districtSelected = d['location_city']['district']['id'];
    // Load cities for district
    this.getCities();
    this.citySelected = d['location_city']['id'];
  }

  // Choices change handlers
  getCities() {
    this.registerForm.controls['location_city'].disable();
    if (this.districtSelected) {
      this.generalService.getDistrictCities(this.districtSelected).then(data => {
        this.cities = data;
        console.log("CITIES", data);
        this.registerForm.controls['location_city'].enable();
      });
    }
  }

  // Form handler
  onSubmit(f: FormGroup) {
    console.log(this.registerForm.value);
    this.invalid = "";

    console.log("VALID?", this.registerForm.valid);
    

    if (this.registerForm.valid) {
      console.log("VALIDDD");
      // Create observer to login
      const loginObserver = {
        next: x => {
          if (x == null) {
            if (!this.edit) {
              this.invalid = "Invalid data. Please make sure you are not already registered."
            } else {
              this.invalid = "Invalid data. Make sure all fields are valid and try again."
            }
          } else {
            this.registerForm.reset();
            this.populateForm(x);
            this.submitted = true;
            this.client = x;
          }
          if (this.edit) {
            localStorage.setItem("name", x['full_name']);
          }
          console.log(x);
        },
        error: err => {
          this.invalid = err.error.message;
        }
      }

      this.registerForm.controls['location_city'].patchValue({'id': this.citySelected});
      console.log("FORM NEW", this.registerForm.value);
      if (!this.edit) {
        this.authService.register(this.registerForm.value).subscribe(loginObserver);
      } else {
        this.authService.update(this.registerForm.value, this.client.email).subscribe(loginObserver);
      }
    } else {
      this.invalid = "Please fill all the fields before submitting."
    }
    this.registerForm.enable();
  }

  redirect() {
    // If user is logged, redirect
    if (this.authService.loggedIn()) {
      this.router.navigate(['/dashboard']);
    }
  }
}
