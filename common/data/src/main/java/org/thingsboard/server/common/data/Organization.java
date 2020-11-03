package org.thingsboard.server.common.data;

import lombok.EqualsAndHashCode;
import org.thingsboard.server.common.data.id.CustomerId;
import org.thingsboard.server.common.data.id.OrganizationId;
import org.thingsboard.server.common.data.id.TenantId;

@EqualsAndHashCode(callSuper = true)
public class Organization extends SearchTextBasedWithAdditionalInfo<OrganizationId> implements HasName, HasTenantId, HasCustomerId {

    private static final long serialVersionUID = 2807343040519543363L;

    private TenantId tenantId;
    private CustomerId customerId;
    private String name;
    private String url;
    private String description;

    public Organization() {
        super();
    }

    public Organization(OrganizationId id) {
        super(id);
    }

    public Organization(Organization organization) {
        super(organization);
        this.tenantId = organization.getTenantId();
        this.customerId = organization.getCustomerId();
        this.url = organization.getUrl();
        this.description = organization.getDescription();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public CustomerId getCustomerId() {
        return customerId;
    }

    public void setCustomerId(CustomerId customerId) {
        this.customerId = customerId;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public TenantId getTenantId() {
        return tenantId;
    }

    public void setTenantId(TenantId tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String getSearchText() {
        return getName();
    }

    @Override
    public String toString() {
        return "Organization{" +
                "tenantId=" + tenantId +
                ", customerId=" + customerId +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", createdTime=" + createdTime +
                ", id=" + id +
                '}';
    }
}
