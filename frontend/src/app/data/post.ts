import {Category} from "./category";

export interface Post {
  id: string;
  title: string;
  content: string;
  creationDate: Date;
  category: Category
}

export type CreatePostRequest = Omit<Post, "id" | "creationDate" | "category"> & {
  categoryId: string;
};

export type UpdatePostRequest = Omit<Post, "creationDate" | "category"> & {
  categoryId: string;
}
