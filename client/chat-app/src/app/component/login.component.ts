import { Component } from '@angular/core';
import { ApiService } from "../services/api.service";

export class User {
  email: string;
  password: string;
}

@Component({
  selector: 'app-login',
  templateUrl: '../template/login.html',
  styleUrls: ['../bootstrap/css/login.css']
})

export class LoginComponent {

  user: User = {
    email : '',
    password : ''
  };
  repsonse: any;

  constructor(private apiService: ApiService) {}

  login() {
    const path = 'login/';
    let data = {
      email : this.user.email,
      password : this.user.password
    };

    console.log("email : " + data.email);
    console.log("pass : " + data.password);
    this.apiService.post(path, data).subscribe(p => {
      console.log("response : " + p);
      this.repsonse = p;
    });
  }
}
