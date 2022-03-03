import {Injectable} from "@angular/core";
import {SecureHttpService} from "./secure-http-service";
import {Advice} from "../models/advice";
import {Joke} from "../models/joke";
import {Meme} from "../models/meme";

@Injectable()
export class MailingService {

  protected url = '/mailing';

  constructor(private secureHttpClient: SecureHttpService) {
  }

  getAdvices(): Promise<Advice[]> {
    const url = this.url + '/advices';
    return this.secureHttpClient.get(url);
  }

  saveAdvice(advice: Advice): Promise<Advice> {
    const url = this.url + '/advices';
    return this.secureHttpClient.post(url, advice);
  }

  updateAdvice(adviceId: number, advice: Advice): Promise<Advice> {
    const url = this.url + '/advices/' + adviceId;
    return this.secureHttpClient.put(url, advice);
  }

  deleteAdvice(adviceId: number): Promise<boolean> {
    const url = this.url + '/advices/' + adviceId;
    return this.secureHttpClient.delete(url);
  }

  getJokes(): Promise<Joke[]> {
    const url = this.url + '/jokes';
    return this.secureHttpClient.get(url);
  }

  saveJoke(joke: Joke): Promise<Joke> {
    const url = this.url + '/jokes';
    return this.secureHttpClient.post(url, joke);
  }

  updateJoke(jokeId: number, joke: Joke): Promise<Joke> {
    const url = this.url + '/jokes/' + jokeId;
    return this.secureHttpClient.put(url, joke);
  }

  deleteJoke(jokeId: number): Promise<boolean> {
    const url = this.url + '/jokes/' + jokeId;
    return this.secureHttpClient.delete(url);
  }

  getMemes(): Promise<Meme[]> {
    const url = this.url + '/memes';
    return this.secureHttpClient.get(url);
  }

  saveMeme(meme: Meme): Promise<Meme> {
    const url = this.url + '/memes';
    return this.secureHttpClient.post(url, meme);
  }

  updateMeme(memeId: number, meme: Meme): Promise<Meme> {
    const url = this.url + '/memes/' + memeId;
    return this.secureHttpClient.put(url, meme);
  }

  deleteMeme(memeId: number): Promise<boolean> {
    const url = this.url + '/memes/' + memeId;
    return this.secureHttpClient.delete(url);
  }

  refreshData(): Promise<boolean> {
    const url = this.url + '/data-refresh';
    return this.secureHttpClient.post(url, null);
  }
}
