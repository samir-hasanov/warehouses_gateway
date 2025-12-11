package www.stock.az.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MyException extends RuntimeException {

    private Integer code;

    public MyException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    //dev
    //branch4

}
