import {Injectable} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

declare const toastr: any;

@Injectable()
export class NotificationService {

  constructor(private translate: TranslateService) {
    toastr.options = {
      closeButton: true,
      debug: false,
      newestOnTop: true,
      progressBar: true,
      positionClass: 'toast-top-center',
      preventDuplicates: false,
      onclick: null,
      showDuration: '1300',
      hideDuration: '1000',
      timeOut: '5000',
      extendedTimeOut: '1000',
      showEasing: 'swing',
      hideEasing: 'linear',
      showMethod: 'fadeIn',
      hideMethod: 'fadeOut'
    };
  }

  success(resourceKey: string, resourceStringParams?: Object) {
    this.translate.get(resourceKey, resourceStringParams).subscribe((res: string) => {
      toastr.success(res);
    });
  }

  info(resourceKey: string, resourceStringParams?: Object) {
    this.translate.get(resourceKey, resourceStringParams).subscribe((res: string) => {
      toastr.info(res);
    });
  }

  error(resourceKey: string, resourceStringParams?: Object) {
    this.translate.get(resourceKey, resourceStringParams).subscribe((res: string) => {
      toastr.error(res);
    });
  }
}
