package br.com.lucasmancan.dojojpa.jpaentitiy

import javax.persistence.*

@Entity
@Table(name = "usuarios")
class UsuarioJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var nome: String? = null,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "permissoes_usuarios",
        joinColumns = [JoinColumn(name = "usuario_id")],
        inverseJoinColumns = [JoinColumn(name = "permissao_id")]
    )
    var permissoes: MutableSet<PermissaoJpaEntity> = mutableSetOf()
)
