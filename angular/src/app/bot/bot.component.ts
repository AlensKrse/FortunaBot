import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-bot',
  templateUrl: './bot.component.html',
  styleUrls: ['./bot.component.css']
})
export class BotComponent implements OnInit {
  chatMailings: any;

  constructor() {
  }

  ngOnInit(): void {
    this.loadChatMailings();
  }

  private loadChatMailings() {

  }
}
