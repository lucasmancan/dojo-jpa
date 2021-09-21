package br.com.lucasmancan.dojojpa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DojoJpaApplication

fun main(args: Array<String>) {
	runApplication<DojoJpaApplication>(*args)
}
