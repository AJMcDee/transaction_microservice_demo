package com.example.tests.testing

import com.example.demo.model.Transaction
import com.example.demo.repository.TransactionRepository
import com.github.tomakehurst.wiremock.common.Json
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import java.time.Duration

class TransactionsIntTest: BaseIntTest() {

    val transaction1 = Transaction(fromIban="1111", toIban="2222", date="01.01.2021", type="transfer", amount=210.00)
    val transaction2 = Transaction(null, "2222", null, "02.02.2021", "withdraw", 220.00)
    val transaction3 = Transaction(null, null, "1111", "02.02.2021", "deposit", 320.00)
    val badTransaction1 = Transaction(3,"2222",null,"04.04.2021","transfer",40.00)

    val testTransactions = listOf(transaction1, transaction2, transaction3)

    @LocalServerPort
    private var port = 0

    private lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var transactionRepository: TransactionRepository

    @BeforeEach
    fun setupWebClient() {
        webTestClient = WebTestClient.bindToServer()
            .baseUrl("http://localhost:$port")
            .responseTimeout(Duration.ofMillis(10000))
            .build()
    }

    @AfterEach
    fun cleanup() {
       transactionRepository.deleteAll().block()
     }

    @Nested
    inner class GetAllTransactions {

        val expectedResult = mutableListOf(transaction1, transaction2, transaction3)

        @Test
        fun `should return all transactions from the database`() {

            transactionRepository.saveAll(testTransactions).subscribe {
                webTestClient.get().uri("/transactions")
                    .exchange()
                    .expectStatus().isOk
                    .expectBody()
                    .json(Json.write(expectedResult))
            }
        }
    }
    @Nested
    inner class GetTransactionById {

        @Test
        fun `should return a specific transaction from the database`() {

            var expectedResult = transaction1

            transactionRepository.save(transaction1).subscribe { result ->
                webTestClient.get().uri("/transaction/${result.id}")
                    .exchange()
                    .expectStatus().isOk
                    .expectBody()
                    .json(Json.write(expectedResult))
            }
        }

        @Test
        fun `requesting a non-existent transaction should return a bad request`() {

                webTestClient.get().uri("/transaction/20")
                    .exchange()
                    .expectStatus().isBadRequest
        }
    }

    @Nested
    inner class AddTransaction {

        @Test
        fun `Posting a transaction should return it`() {
            var expectedResult = transaction2
                webTestClient.post().uri("/transactions")
                    .body(Mono.just(transaction2), Transaction::class.java)
                    .exchange()
                    .expectStatus().isOk
                    .expectBody()
                    .json(Json.write(expectedResult))
        }

        @Test
        fun `Posting a transfer without a to_Iban should return a bad request`() {
            var expectedResult = badTransaction1
            webTestClient.post().uri("/transactions")
                .body(Mono.just(badTransaction1), Transaction::class.java)
                .exchange()
                .expectStatus().isBadRequest
        }

    }

}