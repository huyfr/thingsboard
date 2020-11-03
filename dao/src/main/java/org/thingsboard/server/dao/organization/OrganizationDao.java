package org.thingsboard.server.dao.organization;

import org.thingsboard.server.common.data.Organization;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.dao.Dao;

public interface OrganizationDao extends Dao<Organization> {

    Organization save(TenantId tenantId, Organization camera);
}
