package fr.nathzaf.exercices.blogger.exceptions;

import java.util.UUID;

public class CategoryNotFoundException extends Exception {

    public CategoryNotFoundException(UUID id) {
        super("No Category of id " + id + " found.");
    }
}
