import { Injectable } from '@angular/core';
import {Account} from '../models/Account'
import {Observable,map} from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environments';

@Injectable({
  providedIn: 'root'
})
export class AccountsService {

  constructor(private http:HttpClient ) { }

  searchAccount(id:string,page:number, size:number):Observable<Account>{
    return this.http.get<Account>(environment.backendHost+"/account/"+id+"/pageOperations?page="+page+"&size="+size)}

}
