package org.thingsboard.server.dao.organization;

import org.thingsboard.server.common.data.Organization;
import org.thingsboard.server.common.data.id.OrganizationId;
import org.thingsboard.server.common.data.id.TenantId;

public interface OrganizationService {

    void deleteCamera(TenantId tenantId, OrganizationId cameraId);

    Organization saveCamera(Organization camera);

    Organization saveCameraWithAccessToken(Organization camera, String accessToken);

    Organization findDeviceById(TenantId tenantId, OrganizationId organizationId);

}
