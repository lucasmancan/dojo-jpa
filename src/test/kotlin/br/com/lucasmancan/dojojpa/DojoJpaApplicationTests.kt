package br.com.lucasmancan.dojojpa

import br.com.lucasmancan.dojojpa.repository.VendaRepository
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DojoJpaApplicationTests(private val vendaRepository: VendaRepository) {

    @Test
    fun `Deve buscar venda utilizando projections Spring`(){

    }

    @Test
    fun `Deve buscar venda utilizando named queries`(){

    }


    @Test
    fun `Deve buscar venda utilizando interface projections Spring data`(){

    }

}
