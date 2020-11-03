package org.thingsboard.server.dao.sql.organization;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.thingsboard.server.common.data.Organization;
import org.thingsboard.server.dao.model.sql.OrganizationEntity;
import org.thingsboard.server.dao.organization.OrganizationDao;
import org.thingsboard.server.dao.sql.JpaAbstractSearchTextDao;

import java.util.UUID;

@Component
public class JpaOrganizationDao extends JpaAbstractSearchTextDao<OrganizationEntity, Organization> implements OrganizationDao {

    @Override
    protected Class<OrganizationEntity> getEntityClass() {
        return null;
    }

    @Override
    protected CrudRepository<OrganizationEntity, UUID> getCrudRepository() {
        return null;
    }
}
