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
    MatButtonModule
  ],
  providers: [AuthService, IsAuthenticatedGuard, PageLoadingService, HttpService, SecureHttpService],
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
  ],

  entryComponents: [],
})
export class AppModule {
}

export function HttpLoaderFactory(http: HttpClient): TranslateHttpLoader {
  return new TranslateHttpLoader(http);
}
