package com.example.demo.transactions

import org.springframework.stereotype.Service
import kotlin.math.floor
import com.example.demo.model.*
import com.example.demo.repository.TransactionRepository
import org.springframework.beans.factory.annotation.Autowired
import com.example.demo.model.Transaction
import java.util.*

@Service
class TransactionsService {

    companion object {
        fun generateIBAN() = "DE" + floor(Math.random() * 9999999).toInt()
    }

    @Autowired
    lateinit var repository: TransactionRepository

    fun getAllTransactions(): MutableList<Transaction> {
        var transactions: MutableList<Transaction> = mutableListOf<Transaction>()
        repository.findAll().forEach {
            transactions.add(it)
        }
        return transactions
    }

    fun getTransactionsByIBAN(iban: String): List<Transaction> {
        var allTransactionsByIban = mutableListOf<Transaction>()
        for (outgoingTransaction in repository.findAllByFromIban(iban)) {
            allTransactionsByIban.add(outgoingTransaction)
        }
        for (incomingTransaction in repository.findAllByToIban(iban)) {
            allTransactionsByIban.add(incomingTransaction)
        }

        return allTransactionsByIban

    }


    fun addTransaction(newTransaction: Transaction): Transaction {
        if (!repository.existsTransactionById(newTransaction.id!!)) {
            repository.save(newTransaction)
        }
        return repository.findTransactionById(newTransaction.id!!)

    }
//
//    fun authenticate(loginAttempt : LoginAttempt): String {
//        val iban = loginAttempt.iban
//        val password = loginAttempt.password
//        if (!repository.existsByIbanAndPassword(iban, password)) throw IncorrectCredentialsException()
//
//        val currentAccount = repository.findByIbanAndPassword(iban, password)
//            val token = getNewToken()
//            currentAccount.token = token;
//            repository.save(currentAccount);
//            return token
//    }
//
//
//    fun verifyAccountAccess(token: String) : Account {
//        if (!repository.existsByToken(token)) throw IncorrectCredentialsException()
//        return repository.findByToken(token)
//    }
//
//    fun deleteAccount(token: String) : MutableList<Account> {
//        repository.delete(verifyAccountAccess(token))
//        return getAllTransactions()
//    }
//
//    fun updateBalance(updateRequest: UpdateRequest) : Account {
//        val token = updateRequest.token
//        var currentAccount = verifyAccountAccess(token)
//        currentAccount.updateBalance(updateRequest.amount,updateRequest.operation)
//        repository.save(currentAccount)
//        return currentAccount
//    }
//
//    private fun getNewToken() : String {
//        var token = ""
//        val randomCollection = "r29afKw02Pmlgp9201Odfqzxru"
//        for (i in 1..25) {
//            token += randomCollection[floor(Math.random()*25).toInt()]
//        }
//        return token
//    }

}
