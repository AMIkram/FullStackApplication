import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule,Validators } from '@angular/forms';
import { CustomerService } from '../services/customer.service';
import {Observable, catchError, throwError,of} from 'rxjs';
import { HttpClientModule } from '@angular/common/http';  // <-- Import HttpClient and HttpClientModule
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
@Component({
  selector: 'app-new-customer',
  standalone: true,
  providers:[CustomerService],
  imports: [HttpClientModule,CommonModule,ReactiveFormsModule],
  templateUrl: './new-customer.component.html',
  styleUrl: './new-customer.component.css'
})
export class NewCustomerComponent {
  newCustomerFormGroup! : FormGroup;
  constructor(private fb :FormBuilder, private customerService:CustomerService, private route: Router){}

  ngOnInit():void{
    this.newCustomerFormGroup=this.fb.group({
      name : this.fb.control(null,[Validators.required,Validators.minLength(4)]),
      email : this.fb.control(null,[Validators.required,Validators.email])

      })

    }
  handleSaveCustomer() {
    let customer = this.newCustomerFormGroup.value;
    this.customerService.addCustomer(customer).subscribe({
      next: () => {
        alert("Customer added successfully");
        this.route.navigateByUrl("/customers");
        //to reset the form
        //this.newCustomerFormGroup.reset();


      },
      error: (err) => {
        console.log(err);
      }
    });
  }

}
