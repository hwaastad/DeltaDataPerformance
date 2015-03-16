package org.waastad.deltadataperformance.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.apache.deltaspike.data.api.EntityManagerResolver;

@ApplicationScoped
public class CrmEntityManagerResolver implements EntityManagerResolver {

    @Inject
    private EntityManager em;

    @Override
    public EntityManager resolveEntityManager() {
        return em;
    }

}
