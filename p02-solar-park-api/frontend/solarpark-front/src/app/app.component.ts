import { Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CustomerService } from './services/customer.service';
import { CustomerType } from './models/customer.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  private customerService = inject(CustomerService);

  public customerCollection: CustomerType[] = [];
  public isEditFormVisible = false;
  public isCreateFormVisible = false;
  public selectedCustomer: CustomerType | null = null;

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
    console.log('here');
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
      .subscribe((res) => {
        console.log(res);
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
    this.customerService
      .deleteCustomer(selectedElement.id!)
      .subscribe((res) => {
        console.log(res);
        this.fetchAllCustomers();
      });
  }
}
