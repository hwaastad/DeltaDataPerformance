/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.deltadataperformance.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import org.apache.openejb.junit.jee.EJBContainerRule;
import org.apache.openejb.junit.jee.InjectRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.waastad.deltadataperformance.entity.FlatEntityId;
import org.waastad.deltadataperformance.entity.FlatProduct;

/**
 *
 * @author Helge Waastad <helge.waastad@datametrix.no>
 */
public class AbstractContainerTest {

    @ClassRule
    public static final EJBContainerRule CONTAINER_RULE = new EJBContainerRule();

    @Rule
    public final InjectRule injectRule = new InjectRule(this, CONTAINER_RULE);

    @Inject
    protected DataImporter dataImporter;

    protected static List<FlatProduct> productList = new ArrayList<>();

    public void initialize_array() {
        for (int i = 0; i < 500000; i++) {
            FlatEntityId id = new FlatEntityId(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
            FlatProduct p = new FlatProduct(id);
            productList.add(p);
        }
    }

}
