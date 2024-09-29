import { Routes } from '@angular/router';
import { CustomersComponent } from "./customers/customers.component";
import { AccountComponent } from "./account/account.component";
import { NavbarComponent } from "./navbar/navbar.component";
import { NewCustomerComponent } from './new-customer/new-customer.component';

export const routes: Routes = [

   {path:"customers" , component: CustomersComponent},
   {path:"account" , component: AccountComponent},
   {path:"new-customer" , component: NewCustomerComponent}

  ];
