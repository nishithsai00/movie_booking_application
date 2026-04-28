package com.nishith.reserveShow.model;

public class ErrorMessage {
    private int httpErrorCode;
    private String message;
    private long timestamp;

    public ErrorMessage(int HttpErrorCode, String message, long timestamp) {
        this.httpErrorCode = HttpErrorCode;
        this.message = message;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "ErrorCode=" + httpErrorCode +
                ", message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    public int getHttpErrorCode() {
        return httpErrorCode;
    }

    public void setHttpErrorCode(int httpErrorCode) {
        this.httpErrorCode = httpErrorCode;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
