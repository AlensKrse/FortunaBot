import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatTabChangeEvent} from "@angular/material/tabs";
import {MailingService} from "../services/mailing-service";
import {FormBuilder} from "@angular/forms";
import {AuthService, isNullOrUndefined} from "../services/auth-service";
import {Advice} from "../models/advice";
import {Joke} from "../models/joke";
import {Meme} from "../models/meme";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {NotificationService} from "../services/notification.service";
import {MatDialog} from "@angular/material/dialog";
import {HolidayDetailsComponent} from "../mailing/holidays/holiday-details/holiday-details.component";
import {AdviceDetailsComponent} from "../mailing/advices/advice-details/advice-details.component";
import {MemeDetailsComponent} from "../mailing/memes/meme-details/meme-details.component";
import {HolidayCreationComponent} from "../mailing/holidays/holiday-creation/holiday-creation.component";
import {AdviceCreationComponent} from "../mailing/advices/advice-creation/advice-creation.component";
import {MemeCreationComponent} from "../mailing/memes/meme-creation/meme-creation.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-bot',
  templateUrl: './bot.component.html',
  styleUrls: ['./bot.component.css']
})
export class BotComponent implements AfterViewInit {

  // @ts-ignore
  advices: Advice[];
  // @ts-ignore
  jokes: Joke[];
  // @ts-ignore
  memes: Meme[];
  holidaysDisplayedColumns: string[] = ['id', 'text', 'action'];
  advicesDisplayedColumns: string[] = ['id', 'text', 'action'];
  memesDisplayedColumns: string[] = ['id', 'text', 'action'];
  // @ts-ignore
  holidayDataSource: MatTableDataSource<Joke>;
  // @ts-ignore
  advicesDataSource: MatTableDataSource<Advice>;
  // @ts-ignore
  memesDataSource: MatTableDataSource<Meme>;

  // @ts-ignore
  @ViewChild('advicePaginator') advicePaginator: MatPaginator;
  // @ts-ignore
  @ViewChild('adviceSort') adviceSort: MatSort;

  // @ts-ignore
  @ViewChild('holidayPaginator') holidayPaginator: MatPaginator;
  // @ts-ignore
  @ViewChild('holidaySort') holidaySort: MatSort;

  // @ts-ignore
  @ViewChild('memePaginator') memePaginator: MatPaginator;
  // @ts-ignore
  @ViewChild('memeSort') memeSort: MatSort;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private mailingService: MailingService,
              private notificationService: NotificationService, private dialog: MatDialog, private router: Router) {
  }

  ngOnInit(): void {
    this.loadChatMailings();
  }

  ngAfterViewInit() {
  }

  private loadChatMailings() {
    this.mailingService.getAdvices().then(adviceResult => {
      if (!isNullOrUndefined(adviceResult)) {
        this.advicesDataSource = new MatTableDataSource(adviceResult);
        this.advicesDataSource.paginator = this.advicePaginator;
        this.advicesDataSource.sort = this.adviceSort;
      }
    });
    this.mailingService.getJokes().then(jokeResult => {
      if (!isNullOrUndefined(jokeResult)) {
        this.holidayDataSource = new MatTableDataSource(jokeResult);
        this.holidayDataSource.paginator = this.holidayPaginator;
        this.holidayDataSource.sort = this.holidaySort;
      }
    });
    this.mailingService.getMemes().then(memeResult => {
      if (!isNullOrUndefined(memeResult)) {
        this.memesDataSource = new MatTableDataSource(memeResult);
        this.memesDataSource.paginator = this.memePaginator;
        this.memesDataSource.sort = this.memeSort;
      }
    });
  }

  applyHolidaysFilter(event: KeyboardEvent) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.holidayDataSource.filter = filterValue.trim().toLowerCase();

    if (this.holidayDataSource.paginator) {
      this.holidayDataSource.paginator.firstPage();
    }
  }

  applyAdvicesFilter(event: KeyboardEvent) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.advicesDataSource.filter = filterValue.trim().toLowerCase();

    if (this.advicesDataSource.paginator) {
      this.advicesDataSource.paginator.firstPage();
    }
  }

  applyMemesFilter(event: KeyboardEvent) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.memesDataSource.filter = filterValue.trim().toLowerCase();

    if (this.memesDataSource.paginator) {
      this.memesDataSource.paginator.firstPage();
    }
  }


  openJoke(joke: Joke) {
    const dialogRef = this.dialog.open(HolidayDetailsComponent, {});
    dialogRef.componentInstance.setJoke(joke);
    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }

  openAdvice(advice: Advice) {
    const dialogRef = this.dialog.open(AdviceDetailsComponent, {});
    dialogRef.componentInstance.setAdvice(advice);
    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }

  openMeme(meme: Meme) {
    const dialogRef = this.dialog.open(MemeDetailsComponent, {});
    dialogRef.componentInstance.setMeme(meme);
    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }

  deleteJoke(jokeId: number) {
    if (confirm("Are you sure to delete holiday with id: " + jokeId + "?")) {
      this.mailingService.deleteJoke(jokeId).then(result => {
        if (result) {
          this.notificationService.info("Holiday with id: " + jokeId + " deleted successfully!");
          this.ngOnInit();
        } else {
          this.notificationService.error("Some holiday removal problem with id: " + jokeId);
        }
      })
    }
  }

  deleteAdvice(adviceId: number) {
    if (confirm("Are you sure to delete advice with id: " + adviceId + "?")) {
      this.mailingService.deleteAdvice(adviceId).then(result => {
        if (result) {
          this.notificationService.info("Advice with id: " + adviceId + " deleted successfully!");
          this.ngOnInit();
        } else {
          this.notificationService.error("Some advice removal problem with id: " + adviceId);
        }
      })
    }
  }

  deleteMeme(memeId: number) {
    if (confirm("Are you sure to delete meme with id: " + memeId + "?")) {
      this.mailingService.deleteMeme(memeId).then(result => {
        if (result) {
          this.notificationService.info("Meme with id: " + memeId + " deleted successfully!");
          this.ngOnInit();
        } else {
          this.notificationService.error("Some meme removal problem with id: " + memeId);
        }
      })
    }
  }

  saveHoliday() {
    const dialogRef = this.dialog.open(HolidayCreationComponent, {});
    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }

  saveAdvice() {
    const dialogRef = this.dialog.open(AdviceCreationComponent, {});
    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }

  saveMeme() {
    const dialogRef = this.dialog.open(MemeCreationComponent, {});
    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }

  executeSelectedChange($event: MatTabChangeEvent) {

  }

  refreshData() {
    if (confirm("Are you sure you want to refresh content?")) {
      this.mailingService.refreshData().then(result => {
        if (result) {
          this.notificationService.info("Data refreshed successfully!")
        } else {
          this.notificationService.error("Some problem with refresh..")
        }
      });
    }
  }

  logout() {
    if (confirm("Are you sure you want to logout?")) {
      this.authService.logout();
      this.router.navigateByUrl('/login');
    }
  }
}
