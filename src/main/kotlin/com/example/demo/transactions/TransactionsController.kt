package com.example.demo.transactions

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.*
import org.springframework.beans.factory.annotation.Autowired

import com.example.demo.model.Transaction
import com.example.demo.repository.TransactionRepository


@SpringBootApplication
@CrossOrigin(origins = arrayOf("http://localhost:8081"))
@RestController("/")
class TransactionsController (private val transactionsService: TransactionsService){

    @Autowired
    lateinit var repository: TransactionRepository

    @GetMapping("/transactions")
    fun getAllTransactions(): MutableList<Transaction> {
        return transactionsService.getAllTransactions();
    }

    @GetMapping("/transactions/{iban}")
    fun getTransactionsByIban(@PathVariable iban: String): List<Transaction> {
        return transactionsService.getTransactionsByIBAN(iban)
    }

    @PostMapping(path = ["/transactions"])
    fun addTransaction(@RequestBody newTransaction: Transaction): Transaction {
        return transactionsService.addTransaction(newTransaction);
    }

    @GetMapping("/transaction/{id}")
    fun getTransactionById(@PathVariable id: Int): Transaction {
        return transactionsService.getTransactionById(id)
    }

}