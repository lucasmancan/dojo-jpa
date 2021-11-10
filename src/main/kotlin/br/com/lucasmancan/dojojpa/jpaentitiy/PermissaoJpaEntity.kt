package br.com.lucasmancan.dojojpa.jpaentitiy


import javax.persistence.*

@Entity
@Table(name = "permissoes")
class PermissaoJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permissao_generator")
    //@SequenceGenerator(name="permissao_generator", sequenceName = "permissao_seq")

    //@GeneratedValue(strategy = GenerationType.TABLE, generator = "permissao_generator")
    //@TableGenerator(name="permissao_generator", table="id_generator", schema="dojo_jpa")

    var id: Int? = null,

    @Column(
        name = "nome",
        unique = false,
        nullable = false,
        insertable = true,
        updatable = true,
        columnDefinition = "VARCHAR(45)",
        length = 45
    )
    var nome: String? = null
)

//https://www.baeldung.com/hibernate-identifiers
//https://thorben-janssen.com/jpa-generate-primary-keys/