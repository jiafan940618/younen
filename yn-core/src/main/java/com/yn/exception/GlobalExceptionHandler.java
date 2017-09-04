package com.yn.exception;

import com.yn.vo.re.ResultVOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常处理
 * @author jade
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public Object defaultErrorHandler(MyException e) {
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }
    
}
