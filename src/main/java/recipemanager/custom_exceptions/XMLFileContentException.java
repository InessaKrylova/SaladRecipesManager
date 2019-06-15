package main.java.recipemanager.custom_exceptions;

public class XMLFileContentException extends Exception {
    public XMLFileContentException (String path) {
        super("! Error while reading content of xml file: "+path);
    }
}
