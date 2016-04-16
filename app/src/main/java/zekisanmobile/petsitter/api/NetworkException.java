package zekisanmobile.petsitter.api;

public class NetworkException extends RuntimeException{

    private final int errorCode;

    public NetworkException(int errorCode){
        super("Response code: " + errorCode);
        this.errorCode = errorCode;
    }

}
