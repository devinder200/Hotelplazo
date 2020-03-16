package com.dashboard.util

class ApiGlobalError {
    String code

    ApiGlobalError(String code) {
        this.code = code
    }

    boolean equals(Object o) {
        if (this == o) return true
        if (o == null || getClass() != o.getClass()) return false
        ApiGlobalError that = (ApiGlobalError) o
        return code != null ? code.equals(that.code) : that.code == null
    }

    public int hashCode() {
        return code != null ? code.hashCode() : 0
    }

}