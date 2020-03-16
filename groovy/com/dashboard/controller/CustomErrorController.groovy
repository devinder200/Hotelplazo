package com.dashboard.controller

import com.dashboard.util.CustomHttpErrorResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.context.request.WebRequest

import javax.servlet.http.HttpServletResponse

class CustomErrorController implements ErrorController {
    private static final String PATH = "error"
    @Value('${debug}')
    private boolean debug

    @Autowired
    private ErrorAttributes errorAttributes

    @RequestMapping(PATH)
    @ResponseBody
    public CustomHttpErrorResponse error(WebRequest request, HttpServletResponse response) {
        return new CustomHttpErrorResponse(response.getStatus(), getErrorAttributes(request));
    }

    public void setErrorAttributes(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @Override
    public String getErrorPath() {
        return PATH
    }

    private Map<String, Object> getErrorAttributes(WebRequest request) {
        Map<String, Object> map = new HashMap<>()
        map.putAll(this.errorAttributes.getErrorAttributes(request, this.debug))
        return map
    }
}
