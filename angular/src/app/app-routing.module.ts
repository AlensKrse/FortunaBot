import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {IsAuthenticatedGuard} from "./guard/auth-guard";
import {LoginComponent} from "./login/login.component";
import {BotComponent} from "./bot/bot.component";

const routes: Routes = [
  {
    path: '',
    component: LoginComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'bot',
    component: BotComponent,
    canActivate: [IsAuthenticatedGuard],
  },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes), CommonModule],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
