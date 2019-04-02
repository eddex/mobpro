import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Movie } from '../interfaces/Movie';
import { AlertController, NavController } from '@ionic/angular';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.page.html',
  styleUrls: ['./search.page.scss'],
})
export class SearchPage implements OnInit {

  inputMovieTitle = '';

  constructor(public httpClient: HttpClient,
     public alertController: AlertController,
     public router: Router) { }

  ngOnInit() {
  }

  async presentAlert(message: string) {
    const alert = await this.alertController.create({
      header: 'Alert',
      message: message,
      buttons: ['OK']
    });

    await alert.present();
  }

  searchForMovie() {
    console.log('searchForMovie');
    console.log(this.inputMovieTitle);

    const movie = this.httpClient.get('http://www.omdbapi.com/?apikey=e047e24f&t=' + this.inputMovieTitle);
    movie.subscribe(data => {
      const movieData: Movie = <Movie>data;
      console.log(movieData);

      if (movieData.Response === 'False') {
        this.presentAlert(movieData.Error);
      } else {
        const dataToSend =
`{
  "Title": "${movieData.Title}",
  "Director": "${movieData.Director}",
  "Country": "${movieData.Country}",
  "Year": "${movieData.Year}",
  "Plot": "${movieData.Plot}",
  "Poster": "${movieData.Poster}"
}`;
        try {
          const dataBase64 = btoa(unescape(encodeURIComponent(dataToSend)))
          this.router.navigate([`/movie-details/${dataBase64}`]);
        } catch (e) {
          this.presentAlert('oh no, something went wrong. Error: ' + e);
        }
      }
    });
  }
}
