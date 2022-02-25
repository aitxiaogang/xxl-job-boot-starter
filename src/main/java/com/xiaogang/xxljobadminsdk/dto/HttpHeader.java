package com.xiaogang.xxljobadminsdk.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class HttpHeader implements Serializable {
    private String headerName;
    private String headerValue;

    public HttpHeader(String headerName, String headerValue) {
        this.headerName = headerName;
        this.headerValue = headerValue;
    }
}
