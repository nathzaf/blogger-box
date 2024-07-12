package fr.nathzaf.exercices.blogger.exceptions;

import java.util.UUID;

public class PostNotFoundException extends Exception {

    public PostNotFoundException(UUID id) {
        super("No Post of id " + id + " found.");
    }
}
