package com.example.demo.model

import javax.persistence.*;

@Entity
@Table(name = "transactions")
class Transaction(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    val id: Int? = 1,

    @Column(name = "from_iban")
    val fromIban: String? = null,

    @Column(name = "to_iban")
    var toIban: String? = null,

    @Column(name = "date")
    val date: String = "",

    @Column(name = "type")
    var type: String = "",

    @Column(name = "amount")
    var amount: Double = 0.00

)


