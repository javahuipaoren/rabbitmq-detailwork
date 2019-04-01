package com.geekq.rabbitmqreliable.exception;

import com.geekq.rabbitmqreliable.constants.ResultStatus;

/**
 * @author 邱润泽 bullock
 */
public class GlobalException  extends RuntimeException {

    private static final long serialVersionUID = 349144047714977733L;
    private ResultStatus status;

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(String message, Throwable e) {
        super(message, e);
    }

    public GlobalException(Throwable e) {
        super(e);
    }

    public GlobalException(ResultStatus status) {
        super("[" + status.getCode() + ", " + status + "]");
        this.status = status;
    }

    public GlobalException(String message, ResultStatus status) {
        super(message);
        this.status = status;
    }

    public GlobalException(String message, Throwable cause, ResultStatus status) {
        super(message, cause);
        this.status = status;
    }

    public GlobalException(Throwable cause, ResultStatus status) {
        super(cause);
        this.status = status;
    }

    public ResultStatus getStatus() {
        return this.status;
    }

    public int getErrorCode() {
        return this.status.getCode();
    }

    public String getErrorName() {
        return this.status.getName();
    }
}
