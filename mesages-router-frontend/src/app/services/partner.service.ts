import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Partner } from '../models/partner.model';
import { environment } from '../../environments/environment';
import {PagedResponse} from "../models/page.model";

@Injectable({
  providedIn: 'root'
})
export class PartnerService {
  private apiUrl = `${environment.apiUrl}/partners`;

  constructor(private http: HttpClient) {}

  getPartners(page: number = 0, size: number = 10): Observable<PagedResponse<Partner>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<PagedResponse<Partner>>(this.apiUrl, { params });
  }

  getPartner(id: number): Observable<Partner> {
    return this.http.get<Partner>(`${this.apiUrl}/${id}`);
  }

  addPartner(partner: Omit<Partner, 'id'>): Observable<Partner> {
    return this.http.post<Partner>(this.apiUrl, partner);
  }

  deletePartner(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
