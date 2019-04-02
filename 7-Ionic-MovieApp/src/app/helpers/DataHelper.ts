import { Injectable } from '@angular/core';
import { Movie } from '../interfaces/Movie';

@Injectable()
export class DataHelper {

  constructor() { }

  encodeData(movie: Movie): string {
    const dataToSend =
`{
  "Title": "${movie.Title}",
  "Director": "${movie.Director}",
  "Country": "${movie.Country}",
  "Year": "${movie.Year}",
  "Plot": "${movie.Plot}",
  "Poster": "${movie.Poster}"
}`;
    return btoa(unescape(encodeURIComponent(dataToSend)))
  }

  decodeData(movieDataBase64: string): Movie {
    return <Movie>JSON.parse(decodeURIComponent(escape(atob(movieDataBase64))));
  }

}