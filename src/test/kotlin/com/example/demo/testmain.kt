package com.example.demo

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

fun main() {
    var test = Testmain()
    test.testMono()
}

class Testmain {

    fun testMono() {
        Flux.just("Hello", "there", "Cowboy").log().subscribe(System.out::println)
    }



}