package com.vietdp.spring;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Response implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("data")
    private Object data;

    @JsonProperty("status")
    private boolean status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("title")
    private String title;

    @JsonProperty("return_code")
    private int returnCode;

    public Response() {
        super();
    }

    public Response(final Serializable data, final boolean status, final String message, final int returnCode) {
        super();
        this.data = data;
        this.status = status;
        this.message = message;
        this.returnCode = returnCode;
    }

    public Response(Object data, boolean status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }
}
