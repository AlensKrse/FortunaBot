import {Injectable} from "@angular/core";
import {HttpHeaders} from "@angular/common/http";
import {HttpService} from "./http.service";

@Injectable()
export class AuthService {

  protected url = '/auth';

  token: string;

  constructor(private httpService: HttpService) {
    this.token = '';
  }

  isLoggedIn(): boolean {
    return sessionStorage.getItem('token') !== null;
  }

  saveAuthentication(token: string) {
    sessionStorage.setItem('token', token);
  }

  login(username: string, password: string): Promise<string> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8',
        'Accept': 'application/json',
        'Access-Control-Allow-Origin': '*',
        'X-Requested-With': 'XMLHttpRequest',
        'Access-Control-Allow-Methods': 'GET, POST, PATCH, PUT, DELETE, OPTIONS',
        'Access-Control-Allow-Headers': 'Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With',
        Authorization: 'Basic ' + btoa(username + ':' + password),
      })
    };

    return this.httpService.post(this.url, null, httpOptions);
  }

  getToken(): string {
    // @ts-ignore
    return sessionStorage.getItem('token');
  }
}
