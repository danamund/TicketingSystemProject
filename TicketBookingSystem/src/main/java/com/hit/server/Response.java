package com.hit.server;   // או ה-package שלך בשרת

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Response<T> implements Serializable {
    private String status;
    private Map<String, String> headers = new HashMap<>();
    private T body;

    public Response() {}

    public Response(String status, T body) {
        this.status = status;
        this.body = body;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Map<String, String> getHeaders() { return headers; }
    public void setHeaders(Map<String, String> headers) {
        this.headers = (headers == null) ? new HashMap<>() : headers;
    }

    public T getBody() { return body; }
    public void setBody(T body) { this.body = body; }
}
