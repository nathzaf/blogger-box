package fr.nathzaf.exercices.blogger.dto;

import java.util.UUID;

public class CreationPostRequest {

    private String title;

    private String content;

    private UUID categoryId;

    public CreationPostRequest(String title, String content, UUID categoryID) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }
}
