// Currently inactive - using Mockk instead


//package com.example.tests.testing
//
//import com.example.demo.model.Transaction
//import com.example.demo.repository.TransactionRepository
//import com.example.demo.transactions.TransactionsService
//import org.junit.Test
//import org.junit.jupiter.api.BeforeAll
//import org.junit.jupiter.api.BeforeEach
//import org.mockito.InjectMocks
//import org.mockito.Mock
//import org.mockito.Mockito
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.test.mock.mockito.MockBean
//import kotlin.test.assertEquals
//import kotlin.test.assertNotNull
//
//@SpringBootTest
//class MockitoTests {
//
//    @InjectMocks
//    lateinit var transactionsService: TransactionsService
//
//    @Mock
//    lateinit var transactionRepository : TransactionRepository
//
//    @BeforeEach
//    internal fun beforeEach() {
//        println("Before each running...")
//    }
//
//
//
////    @Mock
////    private var mockRepository = Mockito.mock(TransactionRepository::class.java)
////
////    private val mockTransactionsService: TransactionsService = Mockito.mock(TransactionsService::class.java)
//
//
//
////    @Test
////    fun `add test`() {
////
////        Mockito.`when`(transactionsService.add(2,3)).thenReturn(9)
////        assertEquals(mockTransactionsService.add(2,3), 9)
////
////        //assertEquals(TransactionsService().add(2,3), 6)
////    }
//
//    @Test
//    fun `add transaction`() {
//        var newTransaction = Transaction(null,"7777","8888","01.01.2021","transfer",20.00)
//        Mockito.`when`(transactionRepository.save(newTransaction)).thenReturn(newTransaction)
//        assertNotNull(transactionsService.addTransaction(newTransaction))
//        Mockito.verify(transactionRepository, Mockito.times(1)).save(newTransaction)
//    }
//
//
//}