package org.orderflow.domain;

public class InvalidOrderStateException extends RuntimeException {
    //InvalidOrderStateException IS-A RuntimeException

    public InvalidOrderStateException(String message) {
        super(message); //  Super -> “Call the constructor of my parent class -> RuntimeException(message)
    }
}
