package com.lx.exception.handler;

import com.lx.exception.BaseException;
import com.lx.result.Result;
import com.lx.result.ResultEnum;
import com.lx.result.ResultUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: jyu
 * @Date: 2021/10/10
 * @Description: 全局异常处理器
 **/

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class);

    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) {

        logger.info("logger error IllegalArgumentException");
        System.out.println("IllegalArgumentException:" + Arrays.toString(e.getStackTrace()));
        e.printStackTrace();

        Result errorResult = ResultUtils.error(400, String.format("Parameter %s is Illegal! Please check your parameter and try again!", e.getMessage()));

        response.setStatus(400);

        return errorResult;
    }


    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException e, HttpServletResponse response) {

        Result errorResult = ResultUtils.error(ResultEnum.DATA_BIND_EXCEPTION);

        response.setStatus(400);
        logger.info(e.getMessage());

        return errorResult;
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e, HttpServletResponse response) {
        logger.error(String.format("MethodArgumentNotValidException, message is %s", e.getMessage()));
        response.setStatus(200);
        Result errorResult = ResultUtils.error(ResultEnum.REQUEST_PARAMS_ERROR, convertObjectErrors(e.getBindingResult().getAllErrors()));

        return errorResult;
    }

    private static String convertObjectErrors(List<ObjectError> objectErrors) {
        if (objectErrors != null && objectErrors.size() > 0) {

            StringBuffer stringBuffer = new StringBuffer();

            for (int i = 0; i < objectErrors.size(); i++) {
                String errMsg = objectErrors.get(i).getDefaultMessage();
                if (!StringUtils.isEmpty(errMsg)) {
                    stringBuffer.append(errMsg).append('\n');
                }
            }

            return stringBuffer.toString();
        }

        return "";
    }

    @ExceptionHandler(value = SQLException.class)
    public Result sqlExceptionHandler(SQLException e, HttpServletResponse response) {
        logger.error(String.format("sql exception, message is %s", e.getMessage()));
        response.setStatus(400);

        return ResultUtils.error(ResultEnum.SQL_EXCEPTION);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public Result dataIntegrityViolationExceptionHandler(DataIntegrityViolationException e, HttpServletResponse response) {
        logger.error(String.format("data integrity violation exception, message is %s", e.getMessage()));
        response.setStatus(400);
        Result errorResult = ResultUtils.error(ResultEnum.DATA_INTEGRITY_EXCEPTION);

        return errorResult;
    }

    @ExceptionHandler(value = ServletException.class)
    public Result servletExceptionHandler(ServletException e, HttpServletResponse response) {
        logger.error(String.format("servlet exception, message is %s", e.getMessage()));
        response.setStatus(400);
        return ResultUtils.error(ResultEnum.UN_KNOW_ERROR);
    }

    @ExceptionHandler(value = BaseException.class)
    public Result baseExceptionHandler(BaseException e, HttpServletRequest request, HttpServletResponse response) {
        logger.error(String.format("BusinessException requestURI is %s,message is %s ", request.getRequestURI(), e.getMessage()));
        return ResultUtils.error(e);
    }


    @ExceptionHandler(value = Exception.class)
    public Result defaultExceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) {
        logger.error(String.format("BusinessException requestURI is %s,message is %s ", request.getRequestURI(), e.getMessage()));
        response.setStatus(500);
        return ResultUtils.error(ResultEnum.SERVER_EXCEPTION);
    }

}
