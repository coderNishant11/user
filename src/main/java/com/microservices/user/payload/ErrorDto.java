package com.microservices.user.payload;

import javax.xml.crypto.Data;
import java.util.Date;

public class ErrorDto {

    private String msg;

    private Date date;

    private String url;

    public ErrorDto(String msg, Date date, String url) {
        this.msg = msg;
        this.date = date;
        this.url = url;
    }
}
