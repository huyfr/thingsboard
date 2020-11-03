package org.thingsboard.server.dao.organization;

import org.thingsboard.server.common.data.Organization;

public interface OrganizationService {

    Organization saveCameraWithAccessToken(Organization camera, String accessToken);

}
