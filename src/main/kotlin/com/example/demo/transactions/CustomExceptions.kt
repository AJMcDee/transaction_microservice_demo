package com.example.demo.transactions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="No such account exists")
class AccountNotFoundException: Exception() {

}

@ResponseStatus(value=HttpStatus.FORBIDDEN, reason="Incorrect credentials")
class IncorrectCredentialsException: Exception() {

}