package main.java.recipemanager.custom_exceptions;

public class CreatingElementException extends Exception {
    public CreatingElementException(String element){
        super("Error! " + element + "was not created!");
    }
}
