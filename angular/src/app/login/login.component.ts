import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from "../services/auth-service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  invalidData: boolean;

  form: FormGroup;
  submitted = false;

  constructor(private router: Router, private formBuilder: FormBuilder, private authService: AuthService) {
    this.form = formBuilder.group({
      username: ['', Validators.compose([Validators.required])],
      password: ['', Validators.compose([Validators.required])],
    });
    this.invalidData = false;
    this.submitted = false;
  }

  onLogin() {
    if (this.form.valid) {
      // @ts-ignore
      const username = this.form.get('username').value;
      // @ts-ignore
      const password = this.form.get('password').value;

      this.submitted = true;
      this.authService.login(username, password).then(result => {
        console.log(result);
        if (result) {
          this.invalidData = false;
          this.authService.saveAuthentication(result);
          this.router.navigateByUrl('/bot');
        } else {
          this.invalidData = true;
          this.submitted = false;
          this.authenticationError();
        }
      });
    }
  }


  private authenticationError() {
    // @ts-ignore
    this.form.get('password').setValue('');
  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
  }
}

