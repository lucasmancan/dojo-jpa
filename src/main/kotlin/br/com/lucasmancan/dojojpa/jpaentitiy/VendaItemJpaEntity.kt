package br.com.lucasmancan.dojojpa.jpaentitiy

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "vendas_itens")
class VendaItemJpaEntity(

    @EmbeddedId
    var id: VendaItemId,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false)
    var produto: ProdutoJpaEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false)
    var venda: VendaJpaEntity? = null,

    var quantidade: Int? = null,

    @Column(name = "data_registro")
    var dataRegistro: LocalDateTime? = null,

    @Column(precision = 13, scale = 2)
    var valor: BigDecimal? = null
) {
    @PrePersist
    fun beforeCreate() {
        dataRegistro = LocalDateTime.now()
    }
}


