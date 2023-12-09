import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LabseqService {

  private baseUrl = 'http://localhost:8080/labseq';

  constructor(private http: HttpClient) { }


  getValue(n: number): Observable<BigInt> {
    n = Number(n); //somehow is a string...
    let url = `${this.baseUrl}/${n}`;
    let result = this.http.get(url, {responseType: 'text'}).pipe(
      map(response => BigInt(response))
    );
    return result;
  }
}
