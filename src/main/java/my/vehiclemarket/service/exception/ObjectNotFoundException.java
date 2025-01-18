package my.vehiclemarket.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ObjectNotFoundException  extends RuntimeException{

    public ObjectNotFoundException(String message, Object id) {
        super(message);
    }
}
