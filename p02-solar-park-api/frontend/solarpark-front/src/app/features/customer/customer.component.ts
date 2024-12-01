import { Component, inject } from '@angular/core';
import { CustomerType } from '../../models/customer.model';
import { CustomerService } from '../../services/customer.service';
import {
  DataGridComponent,
  DataGridHeader,
} from '../../components/data-grid/data-grid.component';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'page-customer',
  standalone: true,
  imports: [FormsModule, DataGridComponent],
  templateUrl: './customer.component.html',
  styleUrl: './customer.component.css',
})
export class CustomerPage {
  private customerService = inject(CustomerService);
  private router = inject(Router);

  public customerCollection: CustomerType[] = [];
  public isEditFormVisible = false;
  public isCreateFormVisible = false;
  public selectedCustomer: CustomerType | null = null;

  public dataGridMapping: DataGridHeader[] = [
    { column: 'Customer name', value: 'name' },
    { column: '№ projects', value: 'numberOfProjects' },
  ];

  public ngOnInit() {
    this.fetchAllCustomers();
  }

  public fetchAllCustomers() {
    this.customerService.getAllCustomers().subscribe((res: any) => {
      this.customerCollection = res.data;
    });
  }

  public processOnEdit(selectedElement: CustomerType) {
    this.isEditFormVisible = true;
    this.selectedCustomer = { ...selectedElement };
  }

  public processOnSave() {
    this.customerService
      .updateCustomer(this.selectedCustomer!)
      .subscribe((res: any) => {
        this.fetchAllCustomers();
        console.log(res);
      });
  }

  public processOnCreateCustomer(inputValue: string) {
    this.customerService
      .createNewCustomer({
        name: inputValue,
      })
      .subscribe((_) => {
        this.fetchAllCustomers();
      });
  }

  public processOnChangeCustomerName(customerInput: string) {
    if (this.selectedCustomer) {
      this.selectedCustomer.name = customerInput;
    }
  }

  public processOnCreate() {
    this.isCreateFormVisible = true;
    this.selectedCustomer = null;
  }

  public processOnDelete(selectedElement: CustomerType) {
    this.customerService.deleteCustomer(selectedElement.id!).subscribe((_) => {
      this.fetchAllCustomers();
    });
  }

  public processOnNavigate($customer: CustomerType) {
    this.router.navigateByUrl(`/customers/${$customer.id}/projects`);
  }
}
