import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {TopBarComponent} from "./top-bar/top-bar.components";
import {HttpClientModule} from "@angular/common/http";
import {PostService} from "./services/post.service";
import {PostListComponent} from "./post-list-components/post-list/post-list.component";
import {PostListItemComponent} from "./post-list-components/post-list-item/post-list-item.component";
import {ReactiveFormsModule} from "@angular/forms";
import {CreatePostComponent} from "./create-post-component/create-post.component";
import {CategoryService} from "./services/category.service";
import {SweetAlert2Module} from "@sweetalert2/ngx-sweetalert2";

@NgModule({
  declarations: [
    AppComponent,
    TopBarComponent,
    PostListComponent,
    PostListItemComponent,
    CreatePostComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    SweetAlert2Module
  ],
  providers: [
    PostService,
    CategoryService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
