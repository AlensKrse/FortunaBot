import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {Joke} from "../../../models/joke";
import {MailingService} from "../../../services/mailing-service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {NotificationService} from "../../../services/notification.service";
import {isNullOrUndefined} from "../../../services/auth-service";
import {JokeData} from "../../../models/joke-data";

@Component({
  selector: 'app-holiday-details',
  templateUrl: './holiday-details.component.html',
  styleUrls: ['./holiday-details.component.css']
})
export class HolidayDetailsComponent implements OnInit {

  // @ts-ignore
  joke: Joke;

  // @ts-ignore
  form: FormGroup;

  submitted = false;

  constructor(private dialogRef: MatDialogRef<HolidayDetailsComponent>, private formBuilder: FormBuilder, private mailingService: MailingService, private notificationService: NotificationService) {
    this.form = formBuilder.group({
      id: ['', Validators.compose([Validators.required])],
      text: ['', Validators.compose([Validators.required])]
    });
  }

  ngOnInit(): void {
  }

  setJoke(joke: Joke) {
    this.joke = joke;
    // @ts-ignore
    this.form.get('id').setValue(joke.id);
    // @ts-ignore
    this.form.get('text').setValue(joke.text);
  }

  onSaveClick(): void {
    if (this.form.valid) {
      // @ts-ignore
      const id = this.form.get('id').value;
      // @ts-ignore
      const text = this.form.get('text').value;

      const joke = new JokeData(id, text);
      this.mailingService.updateJoke(id, joke).then(result => {
        if (!isNullOrUndefined(result)) {
          this.notificationService.success("Holiday with id: " + id + " updated successfully!");
          this.onCancelClick();
        } else {
          this.notificationService.error("Some update problem!");
          this.submitted = false;
        }
      });
    }
  }

  onCancelClick(): void {
    this.dialogRef.close();
  }
}
