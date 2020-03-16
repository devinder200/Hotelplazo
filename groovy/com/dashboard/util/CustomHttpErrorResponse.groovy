package com.dashboard.util

class CustomHttpErrorResponse {
    Integer status
    String path
    String errorMessage
    String timeStamp
    String trace

    public CustomHttpErrorResponse(int status, Map<String, Object> errorAttributes) {
        this.status = status
        this.path = (String) errorAttributes.get("path")
        this.errorMessage = (String) errorAttributes.get("message")
        this.timeStamp = errorAttributes.get("timestamp").toString()
        this.trace = (String) errorAttributes.get("trace")
    }
}

