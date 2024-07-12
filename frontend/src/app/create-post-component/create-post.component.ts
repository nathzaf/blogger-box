import {Component, OnInit} from "@angular/core";
import {FormBuilder, Validators} from "@angular/forms";
import {Category} from "../data/category";
import {CategoryService} from "../services/category.service";
import {CreatePostRequest} from "../data/post";
import {PostService} from "../services/post.service";
import {Router} from "@angular/router";
import Swal from "sweetalert2";

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  categories: Category[] = [];

  createPostForm = this.formBuilder.group({
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

  createCategoryForm = this.formBuilder.group({
    name: ['',
      {
        validators: [Validators.required,
          Validators.maxLength(100)],
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

  constructor(private categoryService: CategoryService,
              private postService: PostService,
              private formBuilder: FormBuilder,
              private router: Router) {
  }

  get title() {
    return this.createPostForm.controls['title'];
  }

  get content() {
    return this.createPostForm.controls['content'];
  }

  get categoryId() {
    return this.createPostForm.controls['categoryId'];
  }

  get name() {
    return this.createCategoryForm.controls['name'];
  }

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.categoryService.getCategories().subscribe(categories => {
      this.categories = categories;
    })
  }

  submitCreatePost(): void {
    if (this.createPostForm.valid) {
      const newPost: CreatePostRequest = {
        title: this.createPostForm.value.title!,
        categoryId: this.createPostForm.value.categoryId!,
        content: this.createPostForm.value.content!
      };
      this.postService.createPost(newPost).subscribe(
        () => this.router.navigate(['/']));
      this.Toast.fire({
        icon: "success",
        title: "Post submitted successfully!"
      });
    } else {
      this.Toast.fire({
        icon: "error",
        title: "Check your post and try again"
      });
    }
  }

  submitCreateCategory(): void {
    if (this.createCategoryForm.valid) {
      this.categoryService.createCategory(this.createCategoryForm.value.name!).subscribe((category) => {
        if (category) {
          this.loadCategories();
          this.Toast.fire({
            icon: "success",
            title: "Category created successfully!"
          });
        } else {
          this.Toast.fire({
            icon: "error",
            title: "A category with that name already exists!"
          });
        }
      });
    } else {
      this.Toast.fire({
        icon: "error",
        title: "An error occurred, please try again"
      });
    }
  }
}
