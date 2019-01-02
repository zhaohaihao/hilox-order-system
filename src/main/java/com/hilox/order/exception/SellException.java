package com.hilox.order.exception;

import com.hilox.order.enums.ResultEnum;
import lombok.Getter;

/**
 * Created by Hilox on 2018/12/21 0021.
 */
@Getter
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String defaultMessage) {
        super(defaultMessage);
        this.code = code;
    }
}
