import { Http, Jsonp, Headers, RequestOptions} from '@angular/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import 'rxjs/add/operator/map';

import { app } from '../app.constant';

@Injectable()
export class ApiService {

  constructor(private http: Http) {}

  successCode = 200;
  expiredTokenCode = 444;

  post(path: String, data: any, isEncoded?): Observable<any> {
    console.log("call post function");
    const url = app.url + path;
    console.log("url : " + url);
    let result = this.http.post(url, data, this.getHeaders())
      .map(response => { return response.json(); });
    console.log("resul : " + result);
    return result;
  }

  private getHeaders(): Headers {
    const headers = new Headers();
    headers.append('Accept', 'application/json');
    headers.append();
    return headers;
  }
}
