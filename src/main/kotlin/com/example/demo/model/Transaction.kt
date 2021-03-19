package com.example.demo.model

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("transactions")
class Transaction(

    @Id
    @Column("id")
    var id: Long?,

    @Column("from_iban")
    val fromIban: String? = null,

    @Column("to_iban")
    var toIban: String? = null,

    @Column("date")
    val date: String = "",

    @Column("type")
    var type: String = "",

    @Column("amount")
    var amount: Double = 0.00

)


