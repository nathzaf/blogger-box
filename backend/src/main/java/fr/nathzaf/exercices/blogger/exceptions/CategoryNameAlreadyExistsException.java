package fr.nathzaf.exercices.blogger.exceptions;

public class CategoryNameAlreadyExistsException extends Exception {

    public CategoryNameAlreadyExistsException(String name) {
        super("A Category named " + name + " already exists.");
    }
}
