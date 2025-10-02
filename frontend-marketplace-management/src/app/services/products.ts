import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { INewProductRequest } from '../interfaces/new-product-request';
import { INewProductResponse } from '../interfaces/new-product-response';
import { Observable } from 'rxjs';
import {IProductsResponse} from '../interfaces/products-response';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {
  private readonly _httpClient = inject(HttpClient);

  saveProduct(product: INewProductRequest): Observable<IProductsResponse> {
    return this._httpClient.post<IProductsResponse>('http://localhost:8080/api/products', product);
  }

  getProducts(): Observable<IProductsResponse> {
    return this._httpClient.get<IProductsResponse>('http://localhost:8080/api/products')
  }
}
