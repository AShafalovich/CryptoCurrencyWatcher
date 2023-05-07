package com.ashafalovich.cryptoCurrencyWatcher.utils;

import com.ashafalovich.cryptoCurrencyWatcher.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {

    public static final ResponseEntity<Object> answer(Response response) {
        ResponseEntity result;
        if (null == response) {
            Response error = new Response(500, "General error");
            result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } else {
            Integer code = response.getCode();
            if (null != code && 0 < code) {
                result = ResponseEntity.status(code).body(response);
            } else {
                result = ResponseEntity.ok(response);
            }
        }
        return result;
    }
}
