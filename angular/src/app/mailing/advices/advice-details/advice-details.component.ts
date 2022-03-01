import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {Advice} from "../../../models/advice";
import {MailingService} from "../../../services/mailing-service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {NotificationService} from "../../../services/notification.service";
import {isNullOrUndefined} from "../../../services/auth-service";
import {AdviceData} from "../../../models/advice-data";

@Component({
  selector: 'app-advice-details',
  templateUrl: './advice-details.component.html',
  styleUrls: ['./advice-details.component.css']
})
export class AdviceDetailsComponent implements OnInit {

  // @ts-ignore
  advice: Advice;

  // @ts-ignore
  form: FormGroup;

  submitted = false;

  constructor(private dialogRef: MatDialogRef<AdviceDetailsComponent>, private formBuilder: FormBuilder, private mailingService: MailingService, private notificationService: NotificationService) {
    this.form = formBuilder.group({
      id: ['', Validators.compose([Validators.required])],
      text: ['', Validators.compose([Validators.required])]
    });
  }

  ngOnInit(): void {
  }

  setAdvice(advice: Advice) {
    this.advice = advice;
    // @ts-ignore
    this.form.get('id').setValue(advice.id);
    // @ts-ignore
    this.form.get('text').setValue(advice.text);
  }

  onSaveClick(): void {
    if (this.form.valid) {
      // @ts-ignore
      const id = this.form.get('id').value;
      // @ts-ignore
      const text = this.form.get('text').value;

      const advice = new AdviceData(id, text);
      this.mailingService.updateAdvice(id, advice).then(result => {
        if (!isNullOrUndefined(result)) {
          this.notificationService.success("Advice with id: " + id + " updated successfully!");
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
