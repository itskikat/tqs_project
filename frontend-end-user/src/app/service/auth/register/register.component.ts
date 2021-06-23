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
  invalid: String = "";
  // Options
  districts: District[];
  districtSelected: number;
  cities: City[];
  citySelected: number;
  // Edit
  client: Client;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private generalService: GeneralService,
    public router: Router
  ) {
  }

  ngOnInit(): void {
    this.redirect();

    // Form restrictions
    let d = new Date();
    d.setFullYear(d.getFullYear() - 18);
    this.maxDate = d.getFullYear() + "-0" + d.getMonth() + '-' + d.getDate();
    // Form builder
    this.registerForm = this.fb.group({
      email: ['', Validators.email],
      password: ['', Validators.minLength(3)],
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
            this.invalid = "Invalid data. Please make sure you are not already registered."
          } else {
            this.registerForm.reset();
            this.client = x;
          }
          console.log(x);
        },
        error: err => {
          this.invalid = err.error.message;
        }
      }

      this.registerForm.controls['location_city'].patchValue({'id': this.citySelected});
      console.log("FORM NEW", this.registerForm.value);
      this.authService.register(this.registerForm.value).subscribe(loginObserver);
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
