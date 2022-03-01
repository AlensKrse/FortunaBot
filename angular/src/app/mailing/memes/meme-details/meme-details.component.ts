import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {MailingService} from "../../../services/mailing-service";
import {Meme} from "../../../models/meme";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MemeData} from "../../../models/meme-data";
import {isNullOrUndefined} from "../../../services/auth-service";
import {NotificationService} from "../../../services/notification.service";

@Component({
  selector: 'app-meme-details',
  templateUrl: './meme-details.component.html',
  styleUrls: ['./meme-details.component.css']
})
export class MemeDetailsComponent implements OnInit {

  // @ts-ignore
  meme: Meme;

  // @ts-ignore
  form: FormGroup;

  submitted = false;

  constructor(private dialogRef: MatDialogRef<MemeDetailsComponent>, private formBuilder: FormBuilder, private mailingService: MailingService, private notificationService: NotificationService) {
    this.form = formBuilder.group({
      id: ['', Validators.compose([Validators.required])],
      text: ['', Validators.compose([Validators.required])]
    });
  }

  ngOnInit(): void {
  }

  setMeme(meme: Meme) {
    this.meme = meme;
    // @ts-ignore
    this.form.get('id').setValue(meme.id);
    // @ts-ignore
    this.form.get('text').setValue(meme.text);
  }

  onSaveClick(): void {
    if (this.form.valid) {
      // @ts-ignore
      const id = this.form.get('id').value;
      // @ts-ignore
      const text = this.form.get('text').value;

      const meme = new MemeData(id, text);
      this.mailingService.updateMeme(id, meme).then(result => {
        if (!isNullOrUndefined(result)) {
          this.notificationService.success("Meme with id: " + id + " updated successfully!");
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
