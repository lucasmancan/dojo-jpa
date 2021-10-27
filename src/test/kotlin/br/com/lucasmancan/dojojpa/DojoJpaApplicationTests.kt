package br.com.lucasmancan.dojojpa

import br.com.lucasmancan.dojojpa.entity.VendaEntity
import br.com.lucasmancan.dojojpa.jpaentitiy.VendaJpaEntity
import br.com.lucasmancan.dojojpa.repository.VendaRepository
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DojoJpaApplicationTests(
    @Autowired private val vendaRepository: VendaRepository,
    @Autowired private val testEntityManager: TestEntityManager
) {

    private val logger = LoggerFactory.getLogger(DojoJpaApplicationTests::class.java)

    @Test
    fun `Jpa Projections`() {

        val vendas = testEntityManager.entityManager.createQuery(
            """

            SELECT new br.com.lucasmancan.dojojpa.entity.VendaEntity(v.id, v.dataRegistro, v.valor)
            FROM VendaJpaEntity v
            where v.id =:id

        """.trimIndent(), VendaEntity::class.java
        ).setParameter("id", 1).singleResult

        logger.info(vendas.toString())
    }

    @Test
    fun `Named JPQL Queries`() {
        val vendas =
            testEntityManager.entityManager.createNamedQuery("VendaJpaEntity.findById", VendaJpaEntity::class.java)
                .setParameter("id", 1).singleResult

        logger.info(vendas.toString())
    }

    @Test
    fun `Named Native Queries`() {
        val vendas = testEntityManager.entityManager.createNamedQuery(
            "VendaJpaEntity.findNativeById"
        )
            .setParameter("id", 1)
            .singleResult

        logger.info(vendas.toString())
    }


    @Test
    fun `JPQL Queries`() {
        val vendas = testEntityManager.entityManager.createQuery(
            """

            SELECT v
            FROM VendaJpaEntity v
            LEFT JOIN FETCH v.itens i
            LEFT JOIN i.produto p
            where v.id =:id

        """.trimIndent(), VendaJpaEntity::class.java
        ).setParameter("id", 1).singleResult

        logger.info(vendas.toString())

    }

    @Test
    fun `Interface Projections Spring Data`() {

        val venda = vendaRepository.findVendaById(1)

        logger.info(venda.toString())
    }

}
