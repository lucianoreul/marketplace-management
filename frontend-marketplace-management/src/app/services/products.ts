import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { INewProductRequest } from '../interfaces/new-product-request';
import { Observable } from 'rxjs';
import {IProductsResponse} from '../interfaces/products-response';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {
  private readonly _httpClient = inject(HttpClient);

  saveProduct(product: INewProductRequest): Observable<IProductsResponse> {
    return this._httpClient.post<IProductsResponse>(environment.apiUrl + '/products', product);
  }

  getProducts(): Observable<IProductsResponse> {
    return this._httpClient.get<IProductsResponse>(environment.apiUrl + '/products')
  }
}
