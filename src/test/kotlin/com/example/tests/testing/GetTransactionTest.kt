package com.example.tests.testing


import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.core.IsEqual.equalTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.junit.*
import org.springframework.boot.test.web.client.getForEntity



@SpringBootTest
class GetTransactionTest() {
    @Autowired
    val restTemplate = TestRestTemplate()

    val getEntity = restTemplate.getForEntity<String>("http://localhost:8081/transaction/2")

    @Test
    fun `Getting with Transaction ID returns positive status code`(){
        assertThat(getEntity.statusCode, equalTo(HttpStatus.OK))
        println(getEntity.body)
    }

    @Test
    fun `Getting by IBAN returns the correct transaction`(){
        val bodyWithoutId = getEntity.body.toString().substringAfter("fromIban")
        assertThat(
            bodyWithoutId,
            Matchers.hasToString("\":\"2222\",\"toIban\":\"0000\",\"date\":\"16.03.2021\",\"type\":\"transfer\",\"amount\":100.0}")
        )
    }

    @Test
    fun `Requesting a non-existing Transaction returns a BAD REQUEST status`(){
        val badGetEntity = restTemplate.getForEntity<String>("http://localhost:8081/transaction/99999")
        assertThat(badGetEntity.statusCode, equalTo(HttpStatus.BAD_REQUEST))
    }


}