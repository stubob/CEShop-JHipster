import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { AddressBook } from './address-book.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<AddressBook>;

@Injectable()
export class AddressBookService {

    private resourceUrl =  SERVER_API_URL + 'api/address-books';

    constructor(private http: HttpClient) { }

    create(addressBook: AddressBook): Observable<EntityResponseType> {
        const copy = this.convert(addressBook);
        return this.http.post<AddressBook>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(addressBook: AddressBook): Observable<EntityResponseType> {
        const copy = this.convert(addressBook);
        return this.http.put<AddressBook>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<AddressBook>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AddressBook[]>> {
        const options = createRequestOption(req);
        return this.http.get<AddressBook[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AddressBook[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AddressBook = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<AddressBook[]>): HttpResponse<AddressBook[]> {
        const jsonResponse: AddressBook[] = res.body;
        const body: AddressBook[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to AddressBook.
     */
    private convertItemFromServer(addressBook: AddressBook): AddressBook {
        const copy: AddressBook = Object.assign({}, addressBook);
        return copy;
    }

    /**
     * Convert a AddressBook to a JSON which can be sent to the server.
     */
    private convert(addressBook: AddressBook): AddressBook {
        const copy: AddressBook = Object.assign({}, addressBook);
        return copy;
    }
}
