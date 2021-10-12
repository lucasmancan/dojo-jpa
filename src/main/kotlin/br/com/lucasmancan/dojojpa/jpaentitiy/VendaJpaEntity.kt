package br.com.lucasmancan.dojojpa.jpaentitiy

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "vendas")
@NamedQuery(name = "VendaJpaEntity.findById", query = "SELECT v FROM VendaJpaEntity v where v.id =:id")
class VendaJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,

    @Column(name = "data_registro")
    var dataRegistro: LocalDateTime? = null,

    @Column(name = "data_atualizacao")
    var dataAtualizacao: LocalDateTime? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var usuario: UsuarioJpaEntity? = null,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "venda", orphanRemoval = true)
    var itens: Set<VendaItemJpaEntity> = hashSetOf(),

    @Column(precision = 13, scale = 2)
    var valor: BigDecimal? = null
) {

    @PrePersist
    fun beforeCreate() {
        dataRegistro = LocalDateTime.now()
        dataAtualizacao = LocalDateTime.now()
    }

    @PreUpdate
    fun beforeUpdate() {
        dataAtualizacao = LocalDateTime.now()
    }
}
