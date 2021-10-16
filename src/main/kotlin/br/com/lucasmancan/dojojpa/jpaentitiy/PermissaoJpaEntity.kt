package br.com.lucasmancan.dojojpa.jpaentitiy


import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "permissoes")
class PermissaoJpaEntity(
    @Id
    var id: Int? = null,
    var nome: String? = null
)
