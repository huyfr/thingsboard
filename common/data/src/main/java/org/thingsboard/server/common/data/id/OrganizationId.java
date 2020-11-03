package org.thingsboard.server.common.data.id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.thingsboard.server.common.data.EntityType;

import java.util.UUID;

public class OrganizationId extends UUIDBased implements EntityId {

    private static final long serialVersionUID = 1L;

    @JsonCreator
    public OrganizationId(@JsonProperty("id") UUID id) {
        super(id);
    }

    public static OrganizationId fromString(String organizationId) {
        return new OrganizationId(UUID.fromString(organizationId));
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.CAMERA;
    }

    @Override
    public boolean isNullUid() {
        return false;
    }
}
