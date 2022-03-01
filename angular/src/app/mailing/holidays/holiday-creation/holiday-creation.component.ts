import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";
import {MailingService} from "../../../services/mailing-service";
import {NotificationService} from "../../../services/notification.service";
import {isNullOrUndefined} from "../../../services/auth-service";
import {JokeData} from "../../../models/joke-data";

@Component({
  selector: 'app-holiday-creation',
  templateUrl: './holiday-creation.component.html',
  styleUrls: ['./holiday-creation.component.css']
})
export class HolidayCreationComponent implements OnInit {

  // @ts-ignore
  form: FormGroup;

  submitted = false;

  constructor(private dialogRef: MatDialogRef<HolidayCreationComponent>, private formBuilder: FormBuilder, private mailingService: MailingService, private notificationService: NotificationService) {
    this.form = formBuilder.group({
      text: ['', Validators.compose([Validators.required])]
    });
  }

  ngOnInit(): void {
  }

  onSaveClick(): void {
    if (this.form.valid) {
      // @ts-ignore
      const text = this.form.get('text').value;

      const joke = new JokeData(1, text);
      this.mailingService.saveJoke(joke).then(result => {
        if (!isNullOrUndefined(result)) {
          this.notificationService.success("Holiday with id: " + result.id + " created successfully!");
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
