package org.thingsboard.server.dao.model.sql;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.thingsboard.server.common.data.id.CustomerId;
import org.thingsboard.server.common.data.id.OrganizationId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.Organization;
import org.thingsboard.server.dao.model.BaseSqlEntity;
import org.thingsboard.server.dao.model.ModelConstants;
import org.thingsboard.server.dao.model.SearchTextEntity;
import org.thingsboard.server.dao.util.mapping.JsonStringType;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@TypeDef(name = "json", typeClass = JsonStringType.class)
@MappedSuperclass
public abstract class AbstractOrganizationEntity<T extends Organization> extends BaseSqlEntity<T> implements SearchTextEntity<T> {

    @Column(name = ModelConstants.ORGANIZATION_TENANT_ID_PROPERTY, columnDefinition = "uuid")
    private UUID tenantId;

    @Column(name = ModelConstants.ORGANIZATION_CUSTOMER_ID_PROPERTY, columnDefinition = "uuid")
    private UUID customerId;

    @Column(name = ModelConstants.ORGANIZATION_NAME_PROPERTY)
    private String name;

    @Column(name = ModelConstants.ORGANIZATION_URL_PROPERTY)
    private String url;

    @Column(name = ModelConstants.SEARCH_TEXT_PROPERTY)
    private String searchText;

    @Type(type = "json")
    @Column(name = ModelConstants.ORGANIZATION_ADDITIONAL_INFO_PROPERTY)
    private JsonNode additionalInfo;

    public AbstractOrganizationEntity() {
        super();
    }

    public AbstractOrganizationEntity(Organization organization) {
        if (organization.getId() != null) {
            this.setUuid(organization.getUuidId());
        }
        this.setCreatedTime(organization.getCreatedTime());
        if (organization.getTenantId() != null) {
            this.tenantId = organization.getTenantId().getId();
        }
        if (organization.getCustomerId() != null) {
            this.customerId = organization.getCustomerId().getId();
        }
        this.name = organization.getName();
        this.url = organization.getUrl();
        this.additionalInfo = organization.getAdditionalInfo();
    }

    public AbstractOrganizationEntity(OrganizationEntity organizationEntity) {
        this.setId(organizationEntity.getId());
        this.setCreatedTime(organizationEntity.getCreatedTime());
        this.tenantId = organizationEntity.getTenantId();
        this.customerId = organizationEntity.getCustomerId();
        this.name = organizationEntity.getName();
        this.url = organizationEntity.getUrl();
        this.searchText = organizationEntity.getSearchText();
        this.additionalInfo = organizationEntity.getAdditionalInfo();
    }

    @Override
    public String getSearchTextSource() {
        return name;
    }

    @Override
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    protected Organization toOrganization() {
        Organization organization = new Organization(new OrganizationId(getUuid()));
        organization.setCreatedTime(createdTime);
        if (tenantId != null) {
            organization.setTenantId(new TenantId(tenantId));
        }
        if (customerId != null) {
            organization.setCustomerId(new CustomerId(customerId));
        }
        organization.setName(name);
        organization.setUrl(url);
        organization.setAdditionalInfo(additionalInfo);
        return organization;
    }


}
