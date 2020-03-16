package com.dashboard.validator

import com.dashboard.model.User
import com.dashboard.repository.UserRepository
import com.dashboard.util.ApiErrorCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator

@Component
class UserValidator implements Validator {

    @Autowired
    UserRepository userRepository

    boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass)
    }

    void validate(Object o, Errors errors) {
        User user = (User) o
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty")
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty")
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty")
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty")

        if (!user.username) {
            errors.rejectValue("username", ApiErrorCode.USERNAME_NULL.code)
        } else {
            if (userRepository.findByUsername(user.username)) {
                errors.reject(ApiErrorCode.ALREADY_EXISTS.getCode())
            }
        }

        /* if (!user.confirmPassword.equals(user.password)) {
             errors.reject(ApiErrorCode.PASSWORD_CONFIRM_PASSWORD_NOT_MATCH.getCode())
         }*/
    }
}
