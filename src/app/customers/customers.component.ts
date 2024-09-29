import { Component ,OnInit} from '@angular/core';
import { HttpClientModule } from '@angular/common/http';  // <-- Import HttpClient and HttpClientModule
import { CommonModule } from '@angular/common';
import { CustomerService } from '../services/customer.service';
import {Customer} from '../models/customer'
import {Observable, catchError, throwError,of} from 'rxjs';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { map } from 'rxjs/operators';




@Component({
  selector: 'app-customers',
  standalone: true,
  providers:[CustomerService],
  imports: [HttpClientModule,CommonModule,ReactiveFormsModule],
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.css'
})
export class CustomersComponent  implements OnInit{
  customers!:Observable<Customer[]>;
  errorMessage! : string ;
  searchFormGroup! :FormGroup;
  constructor(private  customerService:CustomerService,private fb:FormBuilder){}
  ngOnInit(){
    this.searchFormGroup=this.fb.group({
      keyWord :this.fb.control("")
      })

    this.customers =this.customerService.getCustomer().pipe(
      catchError((err:any) =>{
        this.errorMessage=err.message;
        return of([]);
        })
      );
    }

  handleSearchCustomer(){
    let keyWord = this.searchFormGroup?.value.keyWord;
    this.customers =this.customerService.searchCustomers(keyWord).pipe(
          catchError((err:any) =>{
            this.errorMessage=err.message;
            return of([]);
            })
          );
        }


  handleDeleteCustomer(c :Customer){
    let conf = confirm("Are you sure?");
    if(!conf) return;
    return this.customerService.deleteCustomer(c).subscribe({
      next :()=>{
        this.customers=this.customers.pipe(
          map((data)=>{

          let index = data.indexOf(c);
            data.slice(index,1);
            return data;
            }));
          },
      error:(err:any)=>{
        console.error("Can't delete customer:", err);;}
      });
    }
}


