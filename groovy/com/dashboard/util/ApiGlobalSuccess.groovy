package com.dashboard.util

class ApiGlobalSuccess {
    String code
    String message

    ApiGlobalSuccess(String code) {
        this.code = code
    }

    ApiGlobalSuccess(String code, String message) {
        this.code = code
        this.message = message
    }

    boolean equals(Object o) {
        if (this == o) return true
        if (!o || getClass() != o.getClass()) return false
        ApiGlobalSuccess that = (ApiGlobalSuccess) o
        return (code ? code.equals(that.code) : that.code == null) && (code ? code.equals(that.code) : that.code == null)
    }

    public int hashCode() {
        int result = code ? code.hashCode() : 0
        result = 31 * result + (message ? message.hashCode() : 0)
        return result
    }
}
