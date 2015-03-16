package org.waastad.deltadataperformance.ejb;

import java.util.UUID;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.openejb.junit.jee.config.PropertyFile;
import org.apache.webbeans.util.CollectionUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.waastad.deltadataperformance.entity.FlatProduct;

@PropertyFile("container.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DataImporterIT extends AbstractContainerTest {

    public DataImporterIT() {
    }

    /**
     * Test of importData method, of class DataImporter.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test_00_initialize_array() throws Exception {
        initialize_array();
    }

    @Test
    public void test_01_import() throws Exception {
        dataImporter.importData(productList);
    }

    @Test
    public void test_02_Change() {
        int index = 100;
        FlatProduct get = productList.get(index);
        get.setPrice(UUID.randomUUID().toString());
        productList.set(index, get);
        dataImporter.importData(productList);
    }

}
