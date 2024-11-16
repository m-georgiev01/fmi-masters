import { HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';

type CustomerType = {
  id: number;
  name: string;
  numberOfProjects: number;
};

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  public httpClient = inject(HttpClient);
  public customerCollection: CustomerType[] = [];

  public ngOnInit() {
    this.httpClient
      .get('http://localhost:8165/customers')
      .subscribe((res: any) => {
        this.customerCollection = res.data;
      });
  }
}
