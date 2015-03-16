package org.waastad.deltadataperformance.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.waastad.deltadataperformance.entity.FlatProduct;
import org.waastad.deltadataperformance.job.DeltaDataRunnable;
import org.waastad.deltadataperformance.job.EmOnlyRunnable;

@Singleton
@Slf4j
public class DataImporter {

    private static final int BUCKETS = 4;
    private static final int BLOCK = 20000;

    @Resource
    private ManagedExecutorService mes;

    @Inject
    private Instance<EmOnlyRunnable> emOnlyRunnables;
    @Inject
    private Instance<DeltaDataRunnable> deltaDataRunnables;

    public void importData(List<FlatProduct> products) {
        int prodCounter = 0;
        Date start = new Date();
        Date stop;
        long diff;
        List<FlatProduct> list = new ArrayList<>();
        for (FlatProduct product : products) {
            prodCounter++;
            list.add(product);

            if ((prodCounter % BLOCK) == 0) {
                try {
                    List<EmOnlyRunnable> partitionList = partitionList(list);
                    Future<?> submit = mes.submit(partitionList.get(0));
                    Future<?> submit1 = mes.submit(partitionList.get(1));
                    Future<?> submit2 = mes.submit(partitionList.get(2));
                    Future<?> submit3 = mes.submit(partitionList.get(3));
                    submit.get();
                    submit1.get();
                    submit2.get();
                    submit3.get();
                } catch (InterruptedException | ExecutionException e) {
                    log.error("Error in thread processing, I will continue", e);
                }
                list.clear();
                stop = new Date();
                diff = (stop.getTime() - start.getTime()) / 1000;
                log.info("Processed {} Products, time spent total: {} seconds", prodCounter, diff);
            }
        }
        try {
            if (!CollectionUtils.isEmpty(list)) {
                List<EmOnlyRunnable> partitionList = partitionList(list);
                for (EmOnlyRunnable partitionList1 : partitionList) {
                    Future<?> submit = mes.submit(partitionList1);
                    submit.get();
                }
            }
            stop = new Date();
            diff = (stop.getTime() - start.getTime()) / 1000;
            log.info("Processed {} Products, time spent total: {} seconds", prodCounter, diff);
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error in thread processing", e);
        }
    }

    public void importDataDeltaOnly(List<FlatProduct> products) {
        int prodCounter = 0;
        Date start = new Date();
        Date stop;
        long diff;
        List<FlatProduct> list = new ArrayList<>();
        for (FlatProduct product : products) {
            prodCounter++;
            list.add(product);

            if ((prodCounter % BLOCK) == 0) {
                try {
                    List<DeltaDataRunnable> partitionList = partitionListDelta(list);
                    Future<?> submit = mes.submit(partitionList.get(0));
                    Future<?> submit1 = mes.submit(partitionList.get(1));
                    Future<?> submit2 = mes.submit(partitionList.get(2));
                    Future<?> submit3 = mes.submit(partitionList.get(3));
                    submit.get();
                    submit1.get();
                    submit2.get();
                    submit3.get();
                } catch (InterruptedException | ExecutionException e) {
                    log.error("Error in thread processing, I will continue", e);
                }
                list.clear();
                stop = new Date();
                diff = (stop.getTime() - start.getTime()) / 1000;
                log.info("Processed {} Products, time spent total: {} seconds", prodCounter, diff);
            }
        }
        try {
            if (!CollectionUtils.isEmpty(list)) {
                List<EmOnlyRunnable> partitionList = partitionList(list);
                for (EmOnlyRunnable partitionList1 : partitionList) {
                    Future<?> submit = mes.submit(partitionList1);
                    submit.get();
                }
            }
            stop = new Date();
            diff = (stop.getTime() - start.getTime()) / 1000;
            log.info("Processed {} Products, time spent total: {} seconds", prodCounter, diff);
        } catch (ExecutionException | InterruptedException e) {
            log.error("Error in thread processing", e);
        }
    }

    private List<EmOnlyRunnable> partitionList(List<FlatProduct> list) {
        List<EmOnlyRunnable> runList = new ArrayList<>();
        int count = list.size() / BUCKETS;
        int reminder = list.size() % BUCKETS;
        if (reminder != 0) {
            count++;
        }
        for (List<FlatProduct> partition1 : ListUtils.partition(list, count)) {
            EmOnlyRunnable run = emOnlyRunnables.get();
            run.setGroupList(partition1);
            runList.add(run);
        }
        return runList;
    }

    private List<DeltaDataRunnable> partitionListDelta(List<FlatProduct> list) {
        List<DeltaDataRunnable> runList = new ArrayList<>();
        int count = list.size() / BUCKETS;
        int reminder = list.size() % BUCKETS;
        if (reminder != 0) {
            count++;
        }
        for (List<FlatProduct> partition1 : ListUtils.partition(list, count)) {
            DeltaDataRunnable run = deltaDataRunnables.get();
            run.setGroupList(partition1);
            runList.add(run);
        }
        return runList;
    }
}
