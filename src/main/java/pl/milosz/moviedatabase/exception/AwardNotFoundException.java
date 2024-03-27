package pl.milosz.moviedatabase.exception;

public class AwardNotFoundException extends RuntimeException {
    public AwardNotFoundException(String message) {
        super(message);
    }
}
