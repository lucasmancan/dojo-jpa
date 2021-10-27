package br.com.lucasmancan.dojojpa.jpaentitiy

import javax.persistence.*

@Entity
@Table(name = "produtos")
class ProdutoJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,
    var nome: String
)
