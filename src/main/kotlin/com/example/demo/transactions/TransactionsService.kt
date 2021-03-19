package com.example.demo.transactions

import org.springframework.stereotype.Service
import kotlin.math.floor
import com.example.demo.model.*
import com.example.demo.repository.TransactionRepository
import org.springframework.beans.factory.annotation.Autowired
import com.example.demo.model.Transaction
import org.springframework.context.annotation.Bean
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class TransactionsService (private val repository: TransactionRepository) {


    fun getAllTransactions(): Mono<MutableList<Transaction>> {
        return repository.findAll().collectList()
    }



    private fun transactionInvalid(newTransaction: Transaction) : Boolean {
        return (newTransaction.type == "transfer" && newTransaction.toIban == null)
    }

    fun addTransaction(newTransaction: Transaction): Mono<Transaction> {
        if (transactionInvalid(newTransaction)) throw TransferToIBANNotFoundException()
        return repository.save(newTransaction)
    }

    fun getTransactionById(id: Long) : Mono<Transaction>  {
        if (repository.existsById(id).equals(false)) {
            throw TransactionNotFoundException()
        }
        return repository.findById(id)
    }

}
