package com.example.tests.testing

import com.example.demo.model.Transaction
import com.example.demo.repository.TransactionRepository
import com.example.demo.transactions.TransactionNotFoundException
import com.example.demo.transactions.TransactionsService
import com.example.demo.transactions.TransferToIBANNotFoundException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.*
import reactor.core.publisher.Flux

import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import kotlin.test.assertFailsWith

class TransactionsServiceTest {

    val repositoryMock = mockk<TransactionRepository>()

    val testService = TransactionsService(repositoryMock)

    val transaction1 = Transaction(0, "1111", "2222", "01.01.2021", "transfer", 210.00)
    val transaction2 = Transaction(1, "2222", null, "02.02.2021", "withdraw", 220.00)
    val transaction3 = Transaction(2, null, "1111", "02.02.2021", "deposit", 320.00)
    val badTransaction1 = Transaction(3,"2222",null,"04.04.2021","transfer",40.00)
    @Nested
    inner class GetAllTransactions {

        @Test
        fun `Function should return Mono of all transactions`() {
            // setup (expected / Rules)
            val expectedResult = mutableListOf(transaction1, transaction2, transaction3)
            every { repositoryMock.findAll() } returns Flux.just(transaction1, transaction2, transaction3)

            // execution
            val result = testService.getAllTransactions()

            StepVerifier.create(result)
                .assertNext {
                    Assertions.assertThat(it).isEqualTo(expectedResult)
                }
                .verifyComplete()
        }


    }

    @Nested
    inner class TransactionInvalid {

        @Test
        fun `An invalid transfer should be flagged as invalid`() {

            val result = testService.transactionInvalid(badTransaction1)
            Assertions.assertThat(result).isTrue

        }

        @Test
        fun `An valid transfer should not be flagged as invalid`() {

            val result = testService.transactionInvalid(transaction1)
            Assertions.assertThat(result).isFalse

        }
    }

    @Nested
    inner class AddTransaction {

        @Test
        fun `Valid transaction should be returned when added`() {
            // setup (expected / Rules)
            val expectedResult = transaction1
            every { repositoryMock.save(transaction1) } returns Mono.just(transaction1)

            // execution
            val result = testService.addTransaction(transaction1)

            StepVerifier.create(result)
                .assertNext {
                    Assertions.assertThat(it).isEqualTo(expectedResult)
                }
                .verifyComplete()
        }

        @Test
        fun `Invalid transaction should throw an error`() {
            // setup (expected / Rules)
            every { repositoryMock.save(transaction1) } returns Mono.just(badTransaction1)

            assertFailsWith<TransferToIBANNotFoundException> {
                testService.addTransaction(badTransaction1)
            }
        }

        @Test
        fun `Valid transfer should not throw an error`() {
            // setup (expected / Rules)
            every { repositoryMock.save(transaction1) } returns Mono.just(transaction1)

            assertDoesNotThrow { testService.addTransaction(transaction1) }

        }
    }

    @Nested
    inner class GetTransactionById {

        @Test
        fun `Valid ID should return the correct transaction`() {
            // setup (expected / Rules)
            val expectedResult = transaction3
            every { repositoryMock.existsById(2) } returns Mono.just(true)
            every { repositoryMock.findById(2) } returns Mono.just(transaction3)

            // execution
            val result = testService.getTransactionById(2)

            StepVerifier.create(result)
                .assertNext {
                    Assertions.assertThat(it).isEqualTo(expectedResult)
                }
                .verifyComplete()
        }

        @Test
        fun `Invalid ID should throw a transaction not found error`() {
            // setup (expected / Rules)

            every { repositoryMock.findById(42) } returns Mono.empty()

            val result = testService.getTransactionById(42)

            StepVerifier.create(result)
                .expectError(TransactionNotFoundException::class.java)
                .verify()
        }

    }

}