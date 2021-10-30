package br.com.lucasmancan.dojojpa

import br.com.lucasmancan.dojojpa.jpaentitiy.VendaJpaEntity
import br.com.lucasmancan.dojojpa.repository.VendaRepository
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PerformanceTests(
    @Autowired private val vendaRepository: VendaRepository,
    @Autowired private val testEntityManager: TestEntityManager
) {

    private val logger = LoggerFactory.getLogger(PerformanceTests::class.java)

    @Test
    fun `Entity Graphs Jpa Performance`() {

        val entityGraph = testEntityManager.entityManager.getEntityGraph("venda-com-items")

        val properties: MutableMap<String, Any> = HashMap()

        properties["javax.persistence.fetchgraph"] = entityGraph

        val start = LocalDateTime.now()

        val vendaJpaEntity: VendaJpaEntity =
            testEntityManager.entityManager
                .find(VendaJpaEntity::class.java, 1, properties)

        val end = LocalDateTime.now()

        val timeDifference = ChronoUnit.MILLIS.between(start, end)


        logger.info("============================================")
        logger.info("============================================")
        logger.info("============================================")
        logger.info("DIFERENÇA DE TEMPO: $timeDifference")
        logger.info("============================================")
        logger.info("============================================")
        logger.info("============================================")
    }

    @Test
    fun `JPQL Jpa Performance`() {


        val start = LocalDateTime.now()

        val vendas = testEntityManager.entityManager.createQuery(
            """

            SELECT v
            FROM VendaJpaEntity v
            LEFT JOIN FETCH v.itens i
            where v.id =:id

        """.trimIndent(), VendaJpaEntity::class.java
        ).setParameter("id", 1).singleResult


        val end = LocalDateTime.now()

        val timeDifference = ChronoUnit.MILLIS.between(start, end)


        logger.info("============================================")
        logger.info("============================================")
        logger.info("============================================")
        logger.info("DIFERENÇA DE TEMPO: $timeDifference")
        logger.info("============================================")
        logger.info("============================================")
        logger.info("============================================")
    }

}
