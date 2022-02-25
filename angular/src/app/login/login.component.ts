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
  submitted: boolean;
  error: boolean;

  form: FormGroup;

  constructor(private router: Router, private formBuilder: FormBuilder, private authService: AuthService) {
    this.form = formBuilder.group({
      username: ['', Validators.compose([Validators.required])],
      password: ['', Validators.compose([Validators.required])],
    });
    this.invalidData = false;
    this.submitted = false;
    this.error = true;
  }

  onLogin() {
    if (this.form.valid) {
      // @ts-ignore
      const username = this.form.get('username').value;
      // @ts-ignore
      const password = this.form.get('password').value;

      this.submitted = true;
      this.authService.login(username, password).subscribe(isValid => {
        if (isValid) {
          this.invalidData = false;
          this.error = false;
          this.authService.saveAuthentication(btoa(username + ':' + password));
          this.router.navigateByUrl('/bot');
        }
      });

      if (this.error) {
        this.invalidData = true;
        this.submitted = false;
        this.authenticationError();
      }
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

