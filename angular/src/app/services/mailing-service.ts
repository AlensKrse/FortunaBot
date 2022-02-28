import {Injectable} from "@angular/core";
import {SecureHttpService} from "./secure-http-service";
import {AdviceData} from "../models/advice-data";
import {JokeData} from "../models/joke-data";
import {MemeData} from "../models/meme-data";

@Injectable()
export class MailingService {

  protected url = '/mailing';

  constructor(private secureHttpClient: SecureHttpService) {
  }

  getAdvices(): Promise<AdviceData[]> {
    const url = this.url + '/advices';
    return this.secureHttpClient.get(url);
  }

  saveAdvice(advice: AdviceData): Promise<AdviceData> {
    const url = this.url + '/advices';
    return this.secureHttpClient.post(url, advice);
  }

  updateAdvice(adviceId: number, advice: AdviceData): Promise<AdviceData> {
    const url = this.url + '/advices/' + adviceId;
    return this.secureHttpClient.put(url, advice);
  }

  deleteAdvice(adviceId: number): Promise<boolean> {
    const url = this.url + '/advices/' + adviceId;
    return this.secureHttpClient.delete(url);
  }

  getJokes(): Promise<JokeData[]> {
    const url = this.url + '/jokes';
    return this.secureHttpClient.get(url);
  }

  saveJoke(joke: JokeData): Promise<JokeData> {
    const url = this.url + '/jokes';
    return this.secureHttpClient.post(url, joke);
  }

  updateJoke(jokeId: number, joke: JokeData): Promise<JokeData> {
    const url = this.url + '/jokes/' + jokeId;
    return this.secureHttpClient.put(url, joke);
  }

  deleteJoke(jokeId: number): Promise<boolean> {
    const url = this.url + '/jokes/' + jokeId;
    return this.secureHttpClient.delete(url);
  }

  getMemes(): Promise<MemeData[]> {
    const url = this.url + '/memes';
    return this.secureHttpClient.get(url);
  }

  saveMeme(meme: MemeData): Promise<MemeData> {
    const url = this.url + '/memes';
    return this.secureHttpClient.post(url, meme);
  }

  updateMeme(memeId: number, meme: MemeData): Promise<MemeData> {
    const url = this.url + '/memes/' + memeId;
    return this.secureHttpClient.put(url, meme);
  }

  deleteMeme(memeId: number): Promise<boolean> {
    const url = this.url + '/memes/' + memeId;
    return this.secureHttpClient.delete(url);
  }

}
