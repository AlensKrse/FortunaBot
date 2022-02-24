import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {PageLoadingService} from "./page-loading-service";

@Injectable()
export class HttpService {

  apiUrl: string;

  constructor(private http: HttpClient, private pageLoadingService: PageLoadingService) {
    this.apiUrl = 'http://localhost:8080';
  }


  public getApiUrl(url: string): string {
    return this.apiUrl + url;
  }

  private static handleError(error: Response | any) {
    let errMsg: string;
    if (error instanceof Response) {
      const body = error || '';
      const err = JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(error);
    return Promise.reject(errMsg);
  }

  public get(url: string, options?: any): any {
    const promise = this.http.get(this.getApiUrl(url), options).toPromise();
    this.pageLoadingService.addPromise(promise);
    return promise.catch(HttpService.handleError);
  }

  public post(url: string, body: any, options?: any): any {
    const promise = this.http.post(this.getApiUrl(url), body, options).toPromise();
    this.pageLoadingService.addPromise(promise);
    return promise.catch(HttpService.handleError);
  }

  public put(url: string, body: any, options?: any, absoluteUrl?: boolean): Promise<any> {
    const resourceUrl = (absoluteUrl === true) ? url : this.getApiUrl(url);
    const promise = this.http.put(resourceUrl, body, options).toPromise();
    this.pageLoadingService.addPromise(promise);
    return promise.catch(HttpService.handleError);
  }

  public delete(url: string, options?: any): any {
    const promise = this.http.delete(this.getApiUrl(url), options).toPromise();
    this.pageLoadingService.addPromise(promise);
    return promise.catch(HttpService.handleError);
  }
}
