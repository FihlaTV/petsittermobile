package zekisanmobile.petsitter.api;

public class NetworkException extends RuntimeException{

    private final int errorCode;

    public NetworkException(int errorCode){
        this.errorCode = errorCode;
    }

    public boolean shouldRetry() {
        return errorCode < 400 || errorCode > 499;
    }
}
