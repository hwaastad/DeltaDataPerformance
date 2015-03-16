package org.waastad.deltadataperformance.repository;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityManagerConfig;
import org.apache.deltaspike.data.api.Repository;
import org.waastad.deltadataperformance.entity.FlatEntityId;
import org.waastad.deltadataperformance.entity.FlatProduct;
import org.waastad.deltadataperformance.producer.CrmEntityManagerResolver;

@Repository
//@EntityManagerConfig(entityManagerResolver = CrmEntityManagerResolver.class)
public abstract class FlatProductRepository extends AbstractEntityRepository<FlatProduct, FlatEntityId> {

}
