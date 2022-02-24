import {Observable, Observer} from "rxjs";
import {Injectable} from "@angular/core";

@Injectable()
export class PageLoadingService {

  private currentPromiseList: Promise<any>[] = [];

  // @ts-ignore
  private loadingObserver: Observer<boolean>;
  public loadingObservable: Observable<boolean>;

  constructor() {
    this.loadingObservable = new Observable<boolean>(observer => {
      this.loadingObserver = observer;
    });
  }

  private start() {
    if (this.loadingObserver) {
      this.loadingObserver.next(true);
    }
  }

  private finish() {
    if (this.loadingObserver) {
      if (this.isPromiseQueueEmpty()) {
        this.loadingObserver.next(false);
      }
    }
  }

  private isPromiseQueueEmpty() {
    return this.currentPromiseList.length === 0;
  }

  addPromise(promise: Promise<any>) {
    this.start();
    this.currentPromiseList.push(promise);

    promise.then(data => {
      this.removeFinishedPromise(promise);
    }, error => {
      this.removeFinishedPromise(promise);
    });
  }

  private removeFinishedPromise(promise: Promise<any>) {
    const index = this.currentPromiseList.indexOf(promise);
    this.currentPromiseList.splice(index, 1);
    this.finish();
  }
}
