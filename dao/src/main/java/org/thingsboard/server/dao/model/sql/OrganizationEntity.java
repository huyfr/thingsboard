package org.thingsboard.server.dao.model.sql;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.TypeDef;
import org.thingsboard.server.common.data.Organization;
import org.thingsboard.server.dao.model.ModelConstants;
import org.thingsboard.server.dao.util.mapping.JsonStringType;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@TypeDef(name = "json", typeClass = JsonStringType.class)
@Table(name = ModelConstants.ORGANIZATION_COLUMN_FAMILY_NAME)
public final class OrganizationEntity extends AbstractOrganizationEntity<Organization> {

    public OrganizationEntity() {
        super();
    }

    public OrganizationEntity(Organization organization) {
        super(organization);
    }

    @Override
    public Organization toData() {
        return toOrganization();
    }
}
