import { Component, OnInit } from '@angular/core';
import { Movie } from '../interfaces/Movie';
import { HttpClient } from '@angular/common/http';
import { DataHelper } from '../helpers/DataHelper';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list',
  templateUrl: 'list.page.html',
  styleUrls: ['list.page.scss']
})
export class ListPage implements OnInit {
  private selectedItem: any;

  public movies: Array<Movie> = [];

  constructor(
    public httpClient: HttpClient,
    private dataHelper: DataHelper,
    public router: Router) {

    for (let i = 1; i <= 5; i++) {
      let movie = this.httpClient.get("./../assets/movies/"+i+".json");
      movie.subscribe(data => {
        this.movies.push(<Movie>data);
      });
    }
  }

  public onClick(movie: Movie) {
    const dataBase64 = this.dataHelper.encodeData(movie);
    this.router.navigate([`/movie-details/${dataBase64}`]);
  }

  ngOnInit() {
  }
}
