import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {catchError, Observable, of} from "rxjs";
import {CreatePostRequest, Post, UpdatePostRequest} from "../data/post";
import {environment} from "../environment/environment";

@Injectable()
export class PostService {

  private postsUrl = `${environment.apiUrl}/v1/posts`

  constructor(private http: HttpClient) {
  }

  getPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(this.postsUrl);
  }

  createPost(newPost: CreatePostRequest): Observable<Post | CreatePostRequest> {
    return this.http.post<Post>(this.postsUrl, newPost)
      .pipe(catchError(this.handleError<CreatePostRequest>('POST', newPost)));
  }

  deletePost(postId: string): Observable<boolean> {
    return this.http.delete<boolean>(`${this.postsUrl}/${postId}`);
  }

  updatePost(updatedPost: UpdatePostRequest): Observable<Post | UpdatePostRequest> {
    return this.http.put<Post>(this.postsUrl, updatedPost)
      .pipe(catchError(this.handleError<UpdatePostRequest>('PUT', updatedPost)));
  }

  protected handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(`${operation} failed: ${error.message}`, error); // log to console
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
