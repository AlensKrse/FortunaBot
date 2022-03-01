import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ReactiveFormsModule} from "@angular/forms";
import {BotComponent} from './bot/bot.component'
import {TranslateLoader, TranslateModule, TranslatePipe} from "@ngx-translate/core";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {AppComponent} from "./app.component";
import {LoginComponent} from "./login/login.component";
import {HeaderComponent} from "./header/header.component";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations'
import {MatFormFieldModule} from "@angular/material/form-field";
import {AlertModule} from "ngx-bootstrap/alert";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {AppRoutingModule} from "./app-routing.module";
import {AuthService} from "./services/auth-service";
import {IsAuthenticatedGuard} from "./guard/auth-guard";
import {PageLoadingService} from "./services/page-loading-service";
import {HttpService} from "./services/http.service";
import {SecureHttpService} from "./services/secure-http-service";
import {MatTabsModule} from "@angular/material/tabs";
import {MatIconModule} from "@angular/material/icon";
import {MatTooltipModule} from "@angular/material/tooltip";
import {MatSortModule} from "@angular/material/sort";
import {MailingService} from "./services/mailing-service";
import {MatTableModule} from "@angular/material/table";
import {MatPaginatorModule} from "@angular/material/paginator";
import {NotificationService} from "./services/notification.service";
import {AdviceDetailsComponent} from './mailing/advices/advice-details/advice-details.component';
import {HolidayDetailsComponent} from './mailing/holidays/holiday-details/holiday-details.component';
import {MemeDetailsComponent} from './mailing/memes/meme-details/meme-details.component'
import {MatDialogModule} from "@angular/material/dialog";
import {AdviceCreationComponent} from './mailing/advices/advice-creation/advice-creation.component';
import {MemeCreationComponent} from './mailing/memes/meme-creation/meme-creation.component';
import {HolidayCreationComponent} from './mailing/holidays/holiday-creation/holiday-creation.component';

@NgModule({
  imports: [
    CommonModule,
    AppRoutingModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    HttpClientModule,
    AlertModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })
    ,
    BrowserAnimationsModule,
    MatInputModule,
    MatButtonModule,
    MatTabsModule,
    MatIconModule,
    MatTooltipModule,
    MatSortModule,
    MatTableModule,
    MatPaginatorModule,
    MatDialogModule
  ],
  providers: [AuthService, IsAuthenticatedGuard, PageLoadingService, HttpService, SecureHttpService, MailingService, NotificationService],
  bootstrap: [AppComponent],
  exports: [
    TranslatePipe,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],

  declarations: [
    LoginComponent,
    AppComponent,
    BotComponent,
    HeaderComponent,
    AdviceDetailsComponent,
    HolidayDetailsComponent,
    MemeDetailsComponent,
    AdviceCreationComponent,
    MemeCreationComponent,
    HolidayCreationComponent],

  entryComponents: [],
})
export class AppModule {
}

export function HttpLoaderFactory(http: HttpClient): TranslateHttpLoader {
  return new TranslateHttpLoader(http);
}
