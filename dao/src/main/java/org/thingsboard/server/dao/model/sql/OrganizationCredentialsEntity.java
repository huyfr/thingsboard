package org.thingsboard.server.dao.model.sql;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.thingsboard.server.common.data.id.OrganizationCredentialsId;
import org.thingsboard.server.common.data.id.OrganizationId;
import org.thingsboard.server.common.data.security.organization.OrganizationCredentials;
import org.thingsboard.server.common.data.security.organization.OrganizationCredentialsType;
import org.thingsboard.server.dao.model.BaseEntity;
import org.thingsboard.server.dao.model.BaseSqlEntity;
import org.thingsboard.server.dao.model.ModelConstants;

import javax.persistence.*;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = ModelConstants.ORGANIZATION_CREDENTIALS_COLUMN_FAMILY_NAME)
public final class OrganizationCredentialsEntity extends BaseSqlEntity<OrganizationCredentials> implements BaseEntity<OrganizationCredentials> {

    @Column(name = ModelConstants.ORGANIZATION_CREDENTIALS_DEVICE_ID_PROPERTY)
    private UUID organizationId;

    @Enumerated(EnumType.STRING)
    @Column(name = ModelConstants.ORGANIZATION_CREDENTIALS_CREDENTIALS_TYPE_PROPERTY)
    private OrganizationCredentialsType credentialsType;

    @Column(name = ModelConstants.ORGANIZATION_CREDENTIALS_CREDENTIALS_ID_PROPERTY)
    private String credentialsId;

    @Column(name = ModelConstants.ORGANIZATION_CREDENTIALS_CREDENTIALS_VALUE_PROPERTY)
    private String credentialsValue;

    public OrganizationCredentialsEntity() {
        super();
    }

    public OrganizationCredentialsEntity(OrganizationCredentials organizationCredentials) {
        if (organizationCredentials.getId() != null) {
            this.setUuid(organizationCredentials.getId().getId());
        }
        this.setCreatedTime(organizationCredentials.getCreatedTime());
        if (organizationCredentials.getOrganizationId() != null) {
            this.organizationId = organizationCredentials.getOrganizationId().getId();
        }
        this.credentialsType = organizationCredentials.getCredentialsType();
        this.credentialsId = organizationCredentials.getCredentialsId();
        this.credentialsValue = organizationCredentials.getCredentialsValue();
    }

    @Override
    public OrganizationCredentials toData() {
        OrganizationCredentials organizationCredentials = new OrganizationCredentials(new OrganizationCredentialsId(this.getUuid()));
        organizationCredentials.setCreatedTime(createdTime);
        if (organizationId != null) {
            organizationCredentials.setOrganizationId(new OrganizationId(organizationId));
        }
        organizationCredentials.setOrganizationCredentialsType(credentialsType);
        organizationCredentials.setCredentialsId(credentialsId);
        organizationCredentials.setCredentialsValue(credentialsValue);
        return organizationCredentials;
    }
}
