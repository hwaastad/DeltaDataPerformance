
package org.waastad.deltadataperformance.job;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import lombok.extern.slf4j.Slf4j;
import org.waastad.deltadataperformance.entity.FlatProduct;
import org.waastad.deltadataperformance.type.Status;

@Slf4j
public class EmOnlyRunnable implements Runnable {

    @Resource
    private UserTransaction utx;
    private List<FlatProduct> groupList;
    @Inject
    private EntityManager em;

    @Override
    public void run() {
        int found = 0;
        int modified = 0;
        int newEntry = 0;

        try {
            if (utx.getStatus() == 6) {
                utx.begin();
            }

            for (FlatProduct group : groupList) {
                FlatProduct findBy = em.find(FlatProduct.class, group.getId());
                if (findBy == null) {
                    newEntry++;
                    group.setStatus(Status.DEFAULT);
                    em.persist(group);
                } else {
                    found++;
                    if (findBy.hasModifiedAttributes(group)) {
                        modified++;
                        log.info("{} is modified and will be updated", group.getId().getSkui());
                        findBy.setDescription(group.getDescription());
                        findBy.setDuration(group.getDuration());
                        findBy.setPrice(group.getPrice());
                        findBy.setProductType(group.getProductType());
                        findBy.setModified(new Date(System.currentTimeMillis()));
                    }
                }
            }
            utx.commit();
//            log.info("Batch size: {}, new: {}, modified: {}", groupList.size(), newEntry, modified);

        } catch (HeuristicMixedException | HeuristicRollbackException | IllegalStateException | NotSupportedException | RollbackException | SecurityException | SystemException e) {
            log.error("Errror in block processing", e);
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                log.error("error rolling back transaction...", ex);
            }
        }
    }

    public void setGroupList(List<FlatProduct> groupList) {
        this.groupList = groupList;
    }

}
