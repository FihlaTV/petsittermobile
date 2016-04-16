package zekisanmobile.petsitter.util;

public class ValidationFailedException extends RuntimeException{

    //region Constructors
    public ValidationFailedException() {
    }

    public ValidationFailedException(String message) {
        super(message);
    }
    //endregion
}
