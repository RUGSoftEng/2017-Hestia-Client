package hestia.backend.exceptions;

import com.rugged.application.hestia.R;
import hestia.UI.HestiaApplication;

public class ComFaultException extends Exception {
    private String error;
    private String message;

    public ComFaultException(String error){
        this.error = error;
        this.message = HestiaApplication.getContext().getString(R.string.errorMessage);
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}