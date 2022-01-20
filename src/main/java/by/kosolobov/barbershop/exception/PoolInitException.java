package by.kosolobov.barbershop.exception;

public class PoolInitException extends PoolException{
    public PoolInitException(String message) {
        super(message);
    }

    public PoolInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public PoolInitException(Throwable cause) {
        super(cause);
    }

    protected PoolInitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
