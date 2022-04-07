package br.com.lucasmancan.dojojpa

import br.com.lucasmancan.dojojpa.entity.VendaEntity
import br.com.lucasmancan.dojojpa.jpaentitiy.VendaJpaEntity
import br.com.lucasmancan.dojojpa.repository.VendaRepository
import org.junit.ClassRule
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit


@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
internal class DojoJpaApplicationTests(
    @Autowired private val vendaRepository: VendaRepository,
    @Autowired private val testEntityManager: TestEntityManager
) {
    class MyPostgreSQLContainer(imageName: String) : MySQLContainer<MyPostgreSQLContainer>(imageName)



    companion object {

        var postgreSQLContainer: MyPostgreSQLContainer = MyPostgreSQLContainer("mysql")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");
        @DynamicPropertySource
        @JvmStatic
        fun databaseProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
            registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
            registry.add("spring.datasource.password", postgreSQLContainer::getPassword);

            postgreSQLContainer.start()
        }
    }



    fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
        TestPropertyValues.of(
            "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
            "spring.datasource.username=" + postgreSQLContainer.getUsername(),
            "spring.datasource.password=" + postgreSQLContainer.getPassword()
        ).applyTo(configurableApplicationContext.environment)
    }

    private val logger = LoggerFactory.getLogger(DojoJpaApplicationTests::class.java)

//    @Test
//    fun `birolea`(){
//       postgreSQLContainer.start()
//
//
//        print("")
//    }

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
    fun ` orm-xml Queries`() {
        val vendas = testEntityManager.entityManager.createNamedQuery(
            "VendaJpaEntity.findByUserId", VendaJpaEntity::class.java
        ).setParameter("usuarioId", 1).singleResult

        logger.info(vendas.toString())
    }


    @Test
    fun `Interface Projections Spring Data`() {

        val venda = vendaRepository.findVendaById(1)

        logger.info(venda.toString())
    }

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
        logger.info("DURAÇÃO Entity Graphs: $timeDifference")
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
        logger.info("DURAÇÃO JPQL: $timeDifference")
        logger.info("============================================")
        logger.info("============================================")
        logger.info("============================================")
    }

}
