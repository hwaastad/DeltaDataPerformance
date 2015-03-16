/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.deltadataperformance.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import lombok.EqualsAndHashCode;
import org.apache.deltaspike.data.api.audit.CreatedOn;
import org.apache.deltaspike.data.api.audit.ModifiedOn;
import org.apache.deltaspike.data.impl.audit.AuditEntityListener;
import org.waastad.deltadataperformance.type.Source;
import org.waastad.deltadataperformance.type.Status;

/**
 *
 * @author Helge Waastad <helge.waastad@datametrix.no>
 */
@Entity
@Table(name = "t_flat_product")
@EntityListeners(AuditEntityListener.class)
@EqualsAndHashCode(of = {"id"})
public class FlatProduct implements Serializable {

    private static final long serialVersionUID = 1516763769148236499L;
    public static final String FIND_BY_KEY = "FlatProduct.findByKey";
    public static final String FIND_BY_SKUI = "FlatProduct.findBySkui";

    @EmbeddedId
    private FlatEntityId id;
    @Basic
    private String price;
    @Basic
    private String description;
    @Basic
    private String duration;
    @Basic
    private String productType;
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedOn
    @Basic
    private Date imported;
    @Temporal(TemporalType.TIMESTAMP)
    @ModifiedOn
    @Basic
    private Date modified;
    @Enumerated(EnumType.STRING)
    private Source source;
    @Enumerated(EnumType.ORDINAL)
    private Status status;
    @Version
    private Long version;

    public FlatProduct() {
    }

    public FlatProduct(FlatEntityId id) {
        this.id = id;
    }

    public FlatProduct(String price, String description, String duration, String productType, Source source) {
        this.price = price;
        this.description = description;
        this.duration = duration;
        this.productType = productType;
        this.source = source;
    }

    public FlatProduct(FlatEntityId id, String price, String description, String duration, String productType, Source source) {
        this.id = id;
        this.price = price;
        this.description = description;
        this.duration = duration;
        this.productType = productType;
        this.source = source;
    }

    public FlatEntityId getId() {
        return id;
    }

    public Source getSource() {
        return source;
    }

    public void setId(FlatEntityId id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Date getImported() {
        return imported;
    }

    public void setImported(Date imported) {
        this.imported = imported;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean hasModifiedAttributes(Object obj) {
        final FlatProduct other = (FlatProduct) obj;
        if (!Objects.equals(this.price, other.price)) {
            return true;
        }
        if (!Objects.equals(this.description, other.description)) {
            return true;
        }
        if (!Objects.equals(this.duration, other.duration)) {
            return true;
        }
        return !Objects.equals(this.productType, other.productType);
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @PrePersist
    public void setStatusOnPersist() {
        this.status = Status.DEFAULT;
    }

    public Long getVersion() {
        return version;
    }

}
