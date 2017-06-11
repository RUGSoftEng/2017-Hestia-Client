package hestia.backend.exceptions.ServerExceptions;

public class UndefinedServerException extends ServerException {
    String message;
        public UndefinedServerException(){
            message= "InternalServerError";
        }
    }

