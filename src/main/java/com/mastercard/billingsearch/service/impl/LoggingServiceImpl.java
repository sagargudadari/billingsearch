package com.mastercard.billingsearch.service.impl;

import com.mastercard.billingsearch.service.LoggingService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mastercard.billingsearch.constant.Constant.CORRELATION_ID;

@Component
@Slf4j
public class LoggingServiceImpl implements LoggingService {

    @Override
    public void logRequest(HttpServletRequest httpServletRequest, Object body) {

        MDC.put(CORRELATION_ID, httpServletRequest.getHeader(CORRELATION_ID));

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Incoming Request : ");
        stringBuilder.append("correlation-id=[").append(httpServletRequest.getHeader(CORRELATION_ID)).append("] ");
        stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] ");
        stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");

        if (body != null) {
            stringBuilder.append("body=[" + body + "] ");
        }

        log.info(stringBuilder.toString());
    }

    @Override
    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Outgoing Response : ");
        stringBuilder.append("correlation-id=[").append(httpServletRequest.getHeader(CORRELATION_ID)).append("] ");
        stringBuilder.append("responseBody=[").append(body).append("] ");

        log.info(stringBuilder.toString());
        MDC.clear();
    }
}