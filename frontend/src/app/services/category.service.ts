import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {catchError, Observable, of} from "rxjs";
import {environment} from "../environment/environment";
import {Category} from "../data/category";

@Injectable()
export class CategoryService {

  private categoriesUrl = `${environment.apiUrl}/v1/categories`

  constructor(private http: HttpClient) {
  }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.categoriesUrl);
  }

  createCategory(newCategoryName: string): Observable<Category | string> {
    return this.http.post<Category>(this.categoriesUrl, newCategoryName)
      .pipe(catchError(this.handleError<string>('POST', undefined)));
  }

  protected handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(`${operation} failed: ${error.message}`, error); // log to console
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
