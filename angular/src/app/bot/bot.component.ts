import {Component, OnInit} from '@angular/core';
import {MatTabChangeEvent} from "@angular/material/tabs";
import {MailingService} from "../services/mailing-service";
import {FormBuilder} from "@angular/forms";
import {AuthService, isNullOrUndefined} from "../services/auth-service";
import {AdviceData} from "../models/advice-data";
import {JokeData} from "../models/joke-data";
import {MemeData} from "../models/meme-data";
import {Sort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-bot',
  templateUrl: './bot.component.html',
  styleUrls: ['./bot.component.css']
})
export class BotComponent implements OnInit {

  // @ts-ignore
  advices: AdviceData[];
  // @ts-ignore
  jokes: JokeData[];
  // @ts-ignore
  memes: MemeData[];

  // @ts-ignore
  advicesDataSource = new MatTableDataSource(this.advices);
  // @ts-ignore
  jokesDataSource = new MatTableDataSource(this.jokes);
  // @ts-ignore
  memesDataSource = new MatTableDataSource(this.memes);


  constructor(private formBuilder: FormBuilder, private authService: AuthService, private mailingService: MailingService) {
  }

  ngOnInit(): void {
    this.loadChatMailings();
  }

  private loadChatMailings() {
    this.mailingService.getAdvices().then(adviceResult => {
      if (!isNullOrUndefined(adviceResult)) {
        this.advices = adviceResult;
      }
    });
    this.mailingService.getJokes().then(jokeResult => {
      if (!isNullOrUndefined(jokeResult)) {
        this.jokes = jokeResult;
      }
    });
    this.mailingService.getMemes().then(memeResult => {
      if (!isNullOrUndefined(memeResult)) {
        this.memes = memeResult;
      }
    });
  }

  executeSelectedChange($event: MatTabChangeEvent) {

  }

  openJoke(joke: JokeData) {

  }

  deleteJoke(joke: JokeData) {

  }


  sortAdviceData(sort: Sort) {
    const data = this.advices.slice();
    if (!sort.active || sort.direction === '') {
      this.advices = data;
      return;
    }

    this.advices = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'id':
          return BotComponent.compare(a.id, b.id, isAsc);
        case 'text':
          return BotComponent.compare(a.text, b.text, isAsc);
        default:
          return 0;
      }
    });
  }

  sortJokeData(sort: Sort) {
    const data = this.jokes.slice();
    if (!sort.active || sort.direction === '') {
      this.jokes = data;
      return;
    }

    this.jokes = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'id':
          return BotComponent.compare(a.id, b.id, isAsc);
        case 'text':
          return BotComponent.compare(a.text, b.text, isAsc);
        default:
          return 0;
      }
    });
  }

  sortMemeData(sort: Sort) {
    const data = this.memes.slice();
    if (!sort.active || sort.direction === '') {
      this.memes = data;
      return;
    }

    this.memes = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'id':
          return BotComponent.compare(a.id, b.id, isAsc);
        case 'text':
          return BotComponent.compare(a.text, b.text, isAsc);
        default:
          return 0;
      }
    });
  }

  private static compare(a: number | string, b: number | string, isAsc: boolean) {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }


  applyAdviceFilter($event: KeyboardEvent) {
    // @ts-ignore
    const filterValue = (event.target as HTMLInputElement).value;
    this.advicesDataSource.filter = filterValue.trim().toLowerCase();
  }

  applyJokesFilter($event: KeyboardEvent) {
    // @ts-ignore
    const filterValue = (event.target as HTMLInputElement).value;
    this.jokesDataSource.filter = filterValue.trim().toLowerCase();
  }

  applyMemesFilter($event: KeyboardEvent) {
    // @ts-ignore
    const filterValue = (event.target as HTMLInputElement).value;
    this.memesDataSource.filter = filterValue.trim().toLowerCase();
  }
}
