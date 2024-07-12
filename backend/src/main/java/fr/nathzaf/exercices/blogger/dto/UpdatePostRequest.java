package fr.nathzaf.exercices.blogger.dto;

import java.util.UUID;

public class UpdatePostRequest {

    private UUID id;

    private String title;

    private String content;

    private UUID categoryId;

    public UpdatePostRequest(UUID id, String title, String content, UUID categoryId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
