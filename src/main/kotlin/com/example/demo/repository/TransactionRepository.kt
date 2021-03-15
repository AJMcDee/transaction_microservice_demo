package com.example.demo.repository

import org.springframework.data.repository.CrudRepository
import com.example.demo.model.Transaction
import org.springframework.stereotype.Repository

@Repository
public interface TransactionRepository: CrudRepository<Transaction, Long> {

    fun findAllByFromIban(iban:String): List<Transaction>
    fun findAllByToIban(iban:String): List<Transaction>

    fun findTransactionById(id: Int): Transaction
    fun existsTransactionById(id:Int): Boolean

}