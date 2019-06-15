package main.java.recipemanager.custom_exceptions;

public class WrongElementIdException extends Exception{
    private int id;

    public int getId(){
        return id;
    }

    public WrongElementIdException(String element, int id){
        super(element + " with id=" + id + " is not found!");
        this.id=id;
    }
}
