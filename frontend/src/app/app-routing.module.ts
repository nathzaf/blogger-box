import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PostListComponent} from "./post-list-components/post-list/post-list.component";
import {CreatePostComponent} from "./create-post-component/create-post.component";

const routes: Routes = [
  {path: '', component: PostListComponent},
  {path: 'add-posts', component: CreatePostComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
