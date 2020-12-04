package cl.rt.schl.utils;
/**
 * 
 * @author Francisco
 *
 */
public class GenericResponse {
    int code;
    String message;
    Object response;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }



}
