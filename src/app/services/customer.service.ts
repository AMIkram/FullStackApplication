import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import { HttpClient } from '@angular/common/http';
import {Customer} from '../models/customer'
import { environment } from '../environments/environments';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  backendHost:String=environment.backendHost;
// tous les operations vont etre ici
  constructor(private http:HttpClient) { }
  ///get post anf put return always an observable
  public getCustomer():Observable<Customer[]>{
    return this.http.get<Customer[]>(this.backendHost+"/customers")
    }

  public searchCustomers(keyWord:string):Observable<Customer[]>{
///when we have paramRequest so in the frontend we put params in a dictionarry and the send it along with URL
    let params : any = {};
    const numericKeyWord = typeof keyWord === 'string' ? Number(keyWord) : keyWord;

    if (!isNaN(numericKeyWord)) {
      params.id = keyWord;
    } else {
      params.name = keyWord;
    }
      return this.http.get<Customer[]>(this.backendHost+"/searchCustomer",{params:params});
      }

    public addCustomer(customer : Customer):Observable<Customer>{

      return this.http.post<Customer>(this.backendHost+"/addCustomer",customer)
      }


    public deleteCustomer(customer : Customer){
          console.log(this.backendHost+"/deleteCustomer/"+customer.id)
          return this.http.delete(this.backendHost+"/deleteCustomer/"+customer.id);
          }
}
