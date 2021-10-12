package br.com.lucasmancan.dojojpa.entity

import java.math.BigDecimal
import java.time.LocalDateTime

data class VendaEntity(val id: Int, val dataRegistro: LocalDateTime, val valor: BigDecimal) {
}