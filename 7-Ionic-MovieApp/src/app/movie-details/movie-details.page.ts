import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Movie } from '../interfaces/Movie';

@Component({
  selector: 'app-movie-details',
  templateUrl: './movie-details.page.html',
  styleUrls: ['./movie-details.page.scss'],
})
export class MovieDetailsPage implements OnInit {

  public movie: Movie;
  constructor(public router: Router) { }

  ngOnInit() {
    const url = this.router.url;
    const dataBase64 = url.split('/')[2].split('%')[0];
    this.movie = <Movie>JSON.parse(decodeURIComponent(escape(atob(dataBase64))));
  }
}
