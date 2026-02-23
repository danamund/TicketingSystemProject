package com.hit.server;

import java.io.Serializable;

public class Response<T> implements Serializable {
    private String status;
    private T body;

    public Response(String status, T body) {
        this.status = status;
        this.body = body;
    }


    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public T getBody() { return body; }
    public void setBody(T body) { this.body = body; }
}