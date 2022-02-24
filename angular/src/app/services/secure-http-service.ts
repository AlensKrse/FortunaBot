import {Injectable} from "@angular/core";
import {AuthService} from "./auth-service";
import {HttpHeaders} from "@angular/common/http";
import {HttpService} from "./http.service";

@Injectable()
export class SecureHttpService {

  constructor(private httpClient: HttpService, private authService: AuthService) {
  }

  private createAuthHeader() {
    const headers = new HttpHeaders({
      Authorization: 'Basic ' + this.authService.getToken(),
    });

    return {
      headers
    };
  }

  public get(url: string): any {
    return this.httpClient.get(url, this.createAuthHeader());
  }

  public post(url: string, body: any): any {
    return this.httpClient.post(url, body, this.createAuthHeader());
  }

  public put(url: string, body?: any, absoluteUrl?: boolean): any {
    return this.httpClient.put(url, body, this.createAuthHeader(), absoluteUrl);
  }

  public delete(url: string): any {
    return this.httpClient.delete(url, this.createAuthHeader());
  }

  public getApiUrl(url: string): string {
    return this.httpClient.getApiUrl(url);
  }
}
