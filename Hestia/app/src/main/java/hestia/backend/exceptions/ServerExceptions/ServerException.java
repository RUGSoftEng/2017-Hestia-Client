package hestia.backend.exceptions.ServerExceptions;

public abstract class ServerException extends Exception {
    String error;

    public ServerException(String message){
        this.error=message;
    }

    public String getError(){
        return error;
    }

}
