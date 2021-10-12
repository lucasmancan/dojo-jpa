package br.com.lucasmancan.dojojpa.jpaentitiy

import javax.persistence.*

@Entity
@Table(name = "usuarios")
class UsuarioJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var nome: String? = null,

    @ManyToMany
    var permissoes: Set<PermissaoJpaEntity> = setOf()
)
