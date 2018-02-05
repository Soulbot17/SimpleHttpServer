package http;

public enum HttpStatus {
    OK("200 OK"),

    //400 statuses
    NOT_FOUND("404 Not Found"),
    FORBIDDEN("403 Forbidden"),

    //500 statuses
    SERVER_ERROR("500 Internal Server Error")
;
    final String status;

    HttpStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
