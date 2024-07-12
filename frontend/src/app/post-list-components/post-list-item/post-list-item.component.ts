import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {Post, UpdatePostRequest} from "../../data/post";
import {PostService} from "../../services/post.service";
import Swal from "sweetalert2";
import {Category} from "../../data/category";
import {CategoryService} from "../../services/category.service";
import {FormBuilder, Validators} from "@angular/forms";

@Component({
  selector: 'app-post-list-item',
  templateUrl: './post-list-item.component.html',
  styleUrls: ['./post-list-item.component.css']
})
export class PostListItemComponent implements OnInit {
  @Input()
  post!: Post;

  categories: Category[] = [];

  // https://stackoverflow.com/questions/43323272/angular-4-call-parent-method-in-a-child-component
  @Output()
  postDeletedEvent = new EventEmitter<string>()

  updatePostForm = this.formBuilder.group({
    title: ['',
      {
        validators: [Validators.required,
          Validators.minLength(5),
          Validators.maxLength(100)],
        updateOn: 'blur'
      }],
    content: ['',
      {
        validators: [Validators.required,
          Validators.maxLength(2500)],
        updateOn: 'blur'
      }],
    categoryId: ['',
      {
        validators: [Validators.required],
        updateOn: 'blur'
      }]
  });
  private Toast = Swal.mixin({
    toast: true,
    position: "top-end",
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
    didOpen: (toast) => {
      toast.onmouseenter = Swal.stopTimer;
      toast.onmouseleave = Swal.resumeTimer;
    }
  });

  constructor(private postService: PostService,
              private categoryService: CategoryService,
              private formBuilder: FormBuilder) {
  }

  get title() {
    return this.updatePostForm.controls['title'];
  }

  get content() {
    return this.updatePostForm.controls['content'];
  }

  get categoryId() {
    return this.updatePostForm.controls['categoryId'];
  }

  ngOnInit(): void {
    this.loadCategories();
    this.setFormValues();
  }

  deletePost(): void {
    if (this.postService.deletePost(this.post.id).subscribe(() =>
      this.callParentReloadPosts())) {
      this.Toast.fire({
        icon: "success",
        title: "Post deleted successfully!"
      });
    } else {
      this.Toast.fire({
        icon: "error",
        title: "An error occurred, please try again!"
      });
    }
  }

  callParentReloadPosts(): void {
    this.postDeletedEvent.next('');
  }

  submitUpdatePost(): void {
    if (this.updatePostForm.valid) {
      const updatedPost: UpdatePostRequest = {
        id: this.post.id,
        title: this.updatePostForm.value.title!,
        categoryId: this.updatePostForm.value.categoryId!,
        content: this.updatePostForm.value.content!
      };
      this.postService.updatePost(updatedPost).subscribe(
        () => this.callParentReloadPosts());
      this.Toast.fire({
        icon: "success",
        title: "Post updated successfully!"
      });
    } else {
      this.Toast.fire({
        icon: "error",
        title: "Check your post and try again"
      });
    }
  }

  loadCategories(): void {
    this.categoryService.getCategories().subscribe(categories => {
      this.categories = categories;
    })
  }

  setFormValues(): void {
    this.updatePostForm.patchValue({
      title: this.post.title,
      content: this.post.content,
      categoryId: this.post.category.id
    });
  }
}
