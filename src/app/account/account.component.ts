import { HttpClientModule } from '@angular/common/http';  // <-- Import HttpClient and HttpClientModule
import { Component } from '@angular/core';
import { AccountsService } from '../services/accounts.service';
import {Account} from '../models/Account'
import { FormBuilder, FormGroup, ReactiveFormsModule,Validators } from '@angular/forms';
import {Observable,of,map} from 'rxjs';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-account',
  standalone: true,
   providers:[AccountsService],
  imports: [HttpClientModule,CommonModule,ReactiveFormsModule],
  templateUrl: './account.component.html',
  styleUrl: './account.component.css'
})
export class AccountComponent {
  accountFormGroup ! : FormGroup;
  operationsFormGroup ! : FormGroup;
  accounts : Account| undefined;
  errorMessage! : any;
  currentPage: number =0;
  pageSize : number =5

  constructor(private fb :FormBuilder, private accountService: AccountsService ){}

  ngOnInit(): void{
    this.accountFormGroup= this.fb.group(
      {
        accountId : this.fb.control('')});

    this.operationsFormGroup= this.fb.group(
          {
            operationType : this.fb.control(null),
            amount : this.fb.control(0),
            description : this.fb.control(null),
            accountDestination : this.fb.control(null)
            })}

  handleSearchAccount(){
    let keyWord = this.accountFormGroup.value.accountId;
    //this.accounts$=this.accountService.searchAccount(keyWord,this.currentPage,this.pageSize);

    this.accountService.searchAccount(keyWord,this.currentPage,this.pageSize).subscribe(data=>{

      this.accounts=data;},
      error=>{
        console.error('error fetching account',error)});

}

gotTopage(page:number){
  this.currentPage=page;
  this.handleSearchAccount();
  }

doOperation(){}
}
