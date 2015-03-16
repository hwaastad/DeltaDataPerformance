package org.waastad.deltadataperformance.producer;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EntityManagerProducer {
    
    @PersistenceContext(unitName = "DeltaPU")
    private EntityManager em;
    
    @Produces
    @RequestScoped
    public EntityManager getEntityManager(){
        log.info("Producing entityManager...");
        return this.em;
    }
}
