package com.example.demo.transactions

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.*
import org.springframework.beans.factory.annotation.Autowired

import com.example.demo.model.Transaction
import com.example.demo.repository.TransactionRepository
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@SpringBootApplication
@CrossOrigin(origins = arrayOf("http://localhost:8081"))
@RestController("/")
class TransactionsController (private val transactionsService: TransactionsService){

//    @Autowired
//    lateinit var repository: TransactionRepository

    @GetMapping("/transactions")
    fun getAllTransactions(): Mono<MutableList<Transaction>> {
        return transactionsService.getAllTransactions();
    }


    @PostMapping(path = ["/transactions"])
    fun addTransaction(@RequestBody newTransaction: Transaction): Mono<Transaction> {
        return transactionsService.addTransaction(newTransaction);
    }

    @GetMapping("/transaction/{id}")
    fun getTransactionById(@PathVariable id: Long): Mono<Transaction> {
            return transactionsService.getTransactionById(id)
    }

}