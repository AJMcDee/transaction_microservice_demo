package com.example.demo.transactions

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.*
import org.springframework.beans.factory.annotation.Autowired

import com.example.demo.model.Transaction
import com.example.demo.repository.TransactionRepository


@SpringBootApplication
@CrossOrigin(origins = arrayOf("http://localhost:8080"))
@RestController("/")
class TransactionsController (private val transactionsService: TransactionsService){

    @Autowired
    lateinit var repository: TransactionRepository

    @GetMapping("/transactions")
    fun sendAccounts(): MutableList<Transaction> {
        return transactionsService.getAllTransactions();
    }

    @GetMapping("/transactions/{iban}")
    fun getTransactionsByIban(@PathVariable iban: String): List<Transaction> {
        return transactionsService.getTransactionsByIBAN(iban)
    }

    @PostMapping(path = ["/transactions"])
    fun addMember(@RequestBody newTransaction: Transaction): Transaction {
        return transactionsService.addTransaction(newTransaction);
    }
//
//    @PostMapping(path = ["/authenticate"])
//    fun authenticate(@RequestBody loginAttempt: LoginAttempt): String {
//        return transactionsService.authenticate(loginAttempt)
//    }
//
//    @DeleteMapping("/account/{token}")
//    fun deleteAccount(@PathVariable token: String): MutableList<Account> {
//        return transactionsService.deleteAccount(token)
//    }
//
//
//    @PatchMapping(path = ["/account"])
//    fun updateBalance(@RequestBody updateRequest: UpdateRequest): Account {
//        return transactionsService.updateBalance(updateRequest)
//
//    }

}