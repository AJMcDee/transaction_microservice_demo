//package com.example.tests.testing
//
//import com.example.demo.model.Transaction
//import com.example.demo.repository.TransactionRepository
//import com.example.demo.transactions.TransactionsService
//import io.mockk.every
//import io.mockk.mockk
//import io.mockk.verify
//import org.assertj.core.api.Assertions
//import org.junit.jupiter.api.*
//import reactor.core.publisher.Flux
//
//import reactor.core.publisher.Mono
//import reactor.test.StepVerifier
//
//class MockkServiceTestSuite {
//
//    val repositoryMock = mockk<TransactionRepository>()
//
//    val testService = TransactionsService(repositoryMock)
//
//    val transaction1 = Transaction(0,"1111","2222","01.01.2021","transfer",210.00)
//    val transaction2 = Transaction(1,"2222",null,"02.02.2021","withdraw",220.00)
//    val transaction3 = Transaction(2,null,"1111","02.02.2021","deposit",320.00)
//
//    @Nested
//    inner class GetAllTransactions {
//
//        @Test
//        fun `Function should return Mono of all transactions`() {
//            // setup (expected / Rules)
//            val expectedResult = mutableListOf(transaction1, transaction2, transaction3)
//            every { repositoryMock.findAllAsMono() } returns Mono.just(expectedResult)
//
//            // execution
//            val result = testService.getAllTransactions()
//
//            // This fails
//            StepVerifier.create(result)
//                .assertNext {
//                    Assertions.assertThat(it).isEqualTo(expectedResult)
//                }
//                .verifyComplete()
//        }
//  }
//}