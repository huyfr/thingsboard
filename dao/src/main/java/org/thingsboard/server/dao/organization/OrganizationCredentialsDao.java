package org.thingsboard.server.dao.organization;

import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.security.organization.OrganizationCredentials;
import org.thingsboard.server.dao.Dao;

import java.util.UUID;

public interface OrganizationCredentialsDao extends Dao<OrganizationCredentials> {

    OrganizationCredentials save(TenantId tenantId, OrganizationCredentials organizationCredentials);

    OrganizationCredentials findByDeviceId(TenantId tenantId, UUID deviceId);

    OrganizationCredentials findByCredentialsId(TenantId tenantId, String credentialsId);
}
