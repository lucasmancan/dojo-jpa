package br.com.lucasmancan.dojojpa.repository

import br.com.lucasmancan.dojojpa.entity.VendaEntity
import br.com.lucasmancan.dojojpa.jpaentitiy.VendaJpaEntity

interface CustomVendaRepository {
    fun getVendaEntityById(id: Int): VendaEntity?
    fun getVendaJPAEntityByIdWithNamedQuery(id: Int): VendaJpaEntity?
}
