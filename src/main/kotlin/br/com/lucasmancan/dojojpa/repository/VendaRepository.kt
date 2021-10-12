package br.com.lucasmancan.dojojpa.repository

import br.com.lucasmancan.dojojpa.jpaentitiy.VendaJpaEntity
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface VendaRepository : PagingAndSortingRepository<VendaJpaEntity, Int>, CustomVendaRepository