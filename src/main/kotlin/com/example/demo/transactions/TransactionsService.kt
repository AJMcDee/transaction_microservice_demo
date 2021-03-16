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

    private fun transactionInvalid(newTransaction: Transaction) : Boolean {
        return (newTransaction.type == "transfer" && newTransaction.toIban == null)
    }

    fun addTransaction(newTransaction: Transaction): Transaction {
        if (transactionInvalid(newTransaction)) throw TransferToIBANNotFoundException()
        return repository.save(newTransaction)
    }

    fun getTransactionById(id: Int) : Transaction {
        if (!repository.existsTransactionById(id)) {
            throw TransactionNotFoundException()
        }
        return repository.findTransactionById(id)
    }

}
