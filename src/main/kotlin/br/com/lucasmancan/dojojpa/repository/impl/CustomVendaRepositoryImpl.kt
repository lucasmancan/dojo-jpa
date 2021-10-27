package br.com.lucasmancan.dojojpa.repository.impl

import br.com.lucasmancan.dojojpa.entity.VendaEntity
import br.com.lucasmancan.dojojpa.jpaentitiy.VendaJpaEntity
import br.com.lucasmancan.dojojpa.repository.CustomVendaRepository
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class CustomVendaRepositoryImpl(private val entityManager: EntityManager) : CustomVendaRepository {
    override fun getVendaEntityById(id: Int): VendaEntity {
        return entityManager.createQuery(
            """
            
            SELECT new br.com.lucasmancan.dojojpa.entity.VendaEntity(v.id, v.dataRegistro, v.valor) 
            FROM VendaJpaEntity v
            where v.id =:id
            
        """.trimIndent(), VendaEntity::class.java
        ).setParameter("id", id).singleResult
    }


    // Cacheadas e validadas no startup da aplicação
    override fun getVendaJPAEntityByIdWithNamedQuery(id: Int): VendaJpaEntity? {
        return entityManager.createNamedQuery("VendaJpaEntity.findById", VendaJpaEntity::class.java).singleResult
    }
}