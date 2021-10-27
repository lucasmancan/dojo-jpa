package br.com.lucasmancan.dojojpa.repository

import java.math.BigDecimal
import java.time.LocalDateTime

interface VendaProjection {
    val id: Int
    val dataRegistro: LocalDateTime
    val valor: BigDecimal
}
