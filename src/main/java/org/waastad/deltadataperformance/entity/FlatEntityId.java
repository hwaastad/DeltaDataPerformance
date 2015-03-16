package org.waastad.deltadataperformance.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Embeddable
@EqualsAndHashCode
@ToString
public class FlatEntityId implements Serializable {

    private static final long serialVersionUID = 5265680071091544109L;

    private String groupName;
    private String subGroup;
    private String skui;

    public FlatEntityId() {
    }

    public FlatEntityId(String groupName, String subGroup, String skui) {
        this.groupName = groupName;
        this.subGroup = subGroup;
        this.skui = skui;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(String subGroup) {
        this.subGroup = subGroup;
    }

    public String getSkui() {
        return skui;
    }

    public void setSkui(String skui) {
        this.skui = skui;
    }

}
