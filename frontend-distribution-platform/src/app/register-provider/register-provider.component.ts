import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'ngx-register-provider',
  templateUrl: './register-provider.component.html',
  styleUrls: ['./register-provider.component.scss']
})
export class RegisterProviderComponent implements OnInit {

  personalDataForm: FormGroup;
  secondForm: FormGroup;
  thirdForm: FormGroup;

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.personalDataForm = this.fb.group({
      firstName: ['', Validators.minLength(3)],
      lastName: ['', Validators.minLength(3)],
      address: ['', Validators.minLength(3)],
    });

    this.secondForm = this.fb.group({
      secondCtrl: ['', Validators.required],
    });

    this.thirdForm = this.fb.group({
      thirdCtrl: ['', Validators.required],
    });
  }
  

  onSecondSubmit() {
    this.secondForm.markAsDirty();
  }

  onThirdSubmit() {
    this.thirdForm.markAsDirty();
  }
}


