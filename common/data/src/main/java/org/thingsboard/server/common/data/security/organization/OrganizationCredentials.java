package org.thingsboard.server.common.data.security.organization;

import lombok.EqualsAndHashCode;
import org.thingsboard.server.common.data.BaseData;
import org.thingsboard.server.common.data.id.OrganizationCredentialsId;
import org.thingsboard.server.common.data.id.OrganizationId;

@EqualsAndHashCode(callSuper = true)
public class OrganizationCredentials extends BaseData<OrganizationCredentialsId> implements OrganizationCredentialsFilter {

    private static final long serialVersionUID = -7869261127032877765L;

    private OrganizationId organizationId;
    private OrganizationCredentialsType organizationCredentialsType;
    private String credentialsId;
    private String credentialsValue;

    public OrganizationCredentials() {
        super();
    }

    public OrganizationCredentials(OrganizationCredentialsId id) {
        super(id);
    }

    public OrganizationCredentials(OrganizationCredentials organizationCredentials) {
        super(organizationCredentials);
        this.organizationId = organizationCredentials.getOrganizationId();
        this.organizationCredentialsType = organizationCredentials.getCredentialsType();
        this.credentialsId = organizationCredentials.getCredentialsId();
        this.credentialsValue = organizationCredentials.getCredentialsValue();
    }

    public OrganizationId getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(OrganizationId organizationId) {
        this.organizationId = organizationId;
    }

    public String getCredentialsValue() {
        return credentialsValue;
    }

    public void setCredentialsValue(String credentialsValue) {
        this.credentialsValue = credentialsValue;
    }

    @Override
    public String getCredentialsId() {
        return credentialsId;
    }

    public void setCredentialsId(String credentialsId) {
        this.credentialsId = credentialsId;
    }

    @Override
    public OrganizationCredentialsType getCredentialsType() {
        return organizationCredentialsType;
    }

    public void setOrganizationCredentialsType(OrganizationCredentialsType organizationCredentialsType) {
        this.organizationCredentialsType = organizationCredentialsType;
    }

    @Override
    public String toString() {
        return "OrganizationCredentials{" +
                "organizationId=" + organizationId +
                ", organizationCredentialsType=" + organizationCredentialsType +
                ", credentialsId='" + credentialsId + '\'' +
                ", credentialsValue='" + credentialsValue + '\'' +
                ", createdTime=" + createdTime +
                ", id=" + id +
                '}';
    }
}
