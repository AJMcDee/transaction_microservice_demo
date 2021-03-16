package com.example.demo.transactions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="No such transaction exists")
class TransactionNotFoundException: Exception() {

}

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Transfers must contain a TO IBAN")
class TransferToIBANNotFoundException: Exception() {

}


