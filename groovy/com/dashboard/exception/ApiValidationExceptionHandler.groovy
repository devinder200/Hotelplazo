package com.dashboard.exception

import com.dashboard.util.ApiErrorsView
import com.dashboard.util.ApiFieldError
import com.dashboard.util.ApiGlobalError
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

import java.util.stream.Collectors

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class ApiValidationExceptionHandler extends ResponseEntityExceptionHandler {

    ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        BindingResult bindingResult = ex.getBindingResult()
        List<ApiFieldError> apiFieldErrors = bindingResult.fieldErrors.stream().map({ fieldError ->
            new ApiFieldError(fieldError.field, fieldError.code, fieldError.rejectedValue)
        }).collect(Collectors.toList())

        List<ApiGlobalError> apiGlobalErrors = bindingResult.globalErrors.stream().map({ globalError ->
            new ApiGlobalError(globalError.code)
        }).collect(Collectors.toList())

        ApiErrorsView apiErrorsView = new ApiErrorsView(apiFieldErrors, apiGlobalErrors)
        return new ResponseEntity<Object>(apiErrorsView, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request"
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST)
    }
}
