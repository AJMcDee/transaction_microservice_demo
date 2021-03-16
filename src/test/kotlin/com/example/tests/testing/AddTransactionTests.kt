package com.example.tests.testing

import com.example.demo.model.Transaction
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasToString
import org.hamcrest.core.IsEqual.equalTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.junit.*
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders


@SpringBootTest
class AddTransactionTests() {


    @Autowired
    val restTemplate = TestRestTemplate()

    val testTransaction: Transaction = Transaction(null, "1111", "2222", "02.03.2021", "transfer", 2.00)
    val headers: HttpHeaders = HttpHeaders()
    var request: HttpEntity<Transaction> = HttpEntity<Transaction>(testTransaction, headers)
    val entity = restTemplate.postForEntity<String>("http://localhost:8081/transactions", request, String.javaClass)


    @Test
    fun `Posting a new transfer returns that transfer`() {
        assertThat(entity.statusCode, equalTo(HttpStatus.OK))
        val bodyWithoutId = entity.body.toString().substringAfter("fromIban")
        assertThat(
            bodyWithoutId,
           hasToString("\":\"1111\",\"toIban\":\"2222\",\"date\":\"02.03.2021\",\"type\":\"transfer\",\"amount\":2.0}")
        )
    }

}