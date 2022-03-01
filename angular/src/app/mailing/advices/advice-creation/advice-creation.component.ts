import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MailingService} from "../../../services/mailing-service";
import {NotificationService} from "../../../services/notification.service";
import {AdviceData} from "../../../models/advice-data";
import {isNullOrUndefined} from "../../../services/auth-service";

@Component({
  selector: 'app-advice-creation',
  templateUrl: './advice-creation.component.html',
  styleUrls: ['./advice-creation.component.css']
})
export class AdviceCreationComponent implements OnInit {

  // @ts-ignore
  form: FormGroup;

  submitted = false;

  constructor(private dialogRef: MatDialogRef<AdviceCreationComponent>, private formBuilder: FormBuilder, private mailingService: MailingService, private notificationService: NotificationService) {
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

      const advice = new AdviceData(1, text);
      this.mailingService.saveAdvice(advice).then(result => {
        if (!isNullOrUndefined(result)) {
          this.notificationService.success("Advice with id: " + result.id + " created successfully!");
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
