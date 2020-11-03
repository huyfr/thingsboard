package org.thingsboard.server.dao.organization;

import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.security.organization.OrganizationCredentials;

public interface OrganizationCredentialsService {
    OrganizationCredentials createCameraCredentials(TenantId tenantId, OrganizationCredentials organizationCredentials);
}
