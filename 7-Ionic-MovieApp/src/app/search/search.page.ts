import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Movie } from '../interfaces/Movie';
import { AlertController } from '@ionic/angular';

@Component({
  selector: 'app-search',
  templateUrl: './search.page.html',
  styleUrls: ['./search.page.scss'],
})
export class SearchPage implements OnInit {

  inputMovieTitle = '';

  constructor(public httpClient: HttpClient, public alertController: AlertController) { }

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
      let movieData: Movie = <Movie>data;
      console.log(movieData);

      if (movieData.Response === 'False') {
        this.presentAlert(movieData.Error);
      } else {
        // TODO: open details view
      }
    });
  }

}
