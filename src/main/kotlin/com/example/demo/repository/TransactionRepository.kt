package com.example.demo.repository


import com.example.demo.model.Transaction
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
public interface TransactionRepository: ReactiveCrudRepository<Transaction, Long> {

    fun findAllByFromIban(iban:String): Mono<MutableList<Transaction>>
    fun findAllByToIban(iban:String): Mono<MutableList<Transaction>>

    fun findTransactionById(id: Long): Transaction


}