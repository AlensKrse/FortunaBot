import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable()
export class AuthService {

  token: string;

  constructor(private httpService: HttpClient) {
    this.token = '';
  }

  isLoggedIn(): boolean {
    return sessionStorage.getItem('token') !== null;
  }

  saveAuthentication(token: string) {
    sessionStorage.setItem('token', token);
  }

  login(username: string, password: string): Observable<Observable<boolean>> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Basic ${window.btoa(username + ":" + password)}`,
      })
    };

    return this.httpService.get<Observable<boolean>>('http://localhost:8080/auth', httpOptions);
  }

  getToken(): string {
    // @ts-ignore
    return sessionStorage.getItem('token');
  }

  logout(): void {
    sessionStorage.clear();
  }
}

export function isNullOrUndefined(value: any) {
  return value === null || value === undefined;
}
