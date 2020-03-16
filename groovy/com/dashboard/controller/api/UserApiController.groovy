package com.dashboard.controller.api

import com.dashboard.command.UserCommand
import com.dashboard.model.User
import com.dashboard.services.UserService
import com.dashboard.util.Authority
import com.dashboard.validator.UserValidator
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.BindingResult
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

@RestController
@RequestMapping(value = "/api/users")
@Slf4j
class UserApiController {
    @Autowired
    UserService userService
    @Autowired
    UserValidator userValidator

    @InitBinder("user")
    void setupBinder(WebDataBinder binder) {
        binder.addValidators(userValidator)
    }

    @PostMapping(value = "/create")
    HttpEntity<?> register(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<?>(result.allErrors, HttpStatus.OK)
        }
        user.authorityList = [Authority.ROLE_USER]
        userService.save(user)
        return new ResponseEntity<User>(user, HttpStatus.CREATED)
    }

    @GetMapping(value = "/confirm/{uuid}")
    HttpEntity<?> confirmUser(@PathVariable("uuid") String uuid) {
        User user = userService.confirmUser(uuid)
        return new ResponseEntity<User>(user, HttpStatus.CREATED)
    }

    @GetMapping(value = "/isLogin")
    public def isLogin() {
        def authentication = SecurityContextHolder.getContext().getAuthentication()
        return authentication?.principal
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id) {
        userService.delete(id)
    }

    @GetMapping(value = "/")
    public List<UserCommand> listUsers() {
        userService.findAll()
    }

    @GetMapping(value = "/{uuid}")
    public HttpEntity<?> fetchUserById(@PathVariable String uuid) {
        User user = userService.findByUUID(uuid)
        return new ResponseEntity<UserCommand>(new UserCommand(user), HttpStatus.OK)
    }

    @PutMapping(value = "/{id}")
    HttpEntity<?> update(@RequestBody User user, @PathVariable String id) {
        if (!userService.findById(id)) {
            return new ResponseEntity<?>(HttpStatus.NOT_FOUND)
        }
        userService.update(id, user)
        return new ResponseEntity<User>(user, HttpStatus.OK)
    }
}

