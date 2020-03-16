package com.dashboard.util

class ApiErrorsView {
    List<ApiFieldError> fieldErrors
    List<ApiGlobalError> globalErrors

    ApiErrorsView(List<ApiFieldError> fieldErrors, List<ApiGlobalError> globalErrors) {
        this.fieldErrors = fieldErrors
        this.globalErrors = globalErrors
    }
}
