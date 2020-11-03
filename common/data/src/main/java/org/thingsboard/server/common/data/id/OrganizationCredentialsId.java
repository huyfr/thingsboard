package org.thingsboard.server.common.data.id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class OrganizationCredentialsId extends UUIDBased {

    @JsonCreator
    public OrganizationCredentialsId(@JsonProperty("id") UUID id) {
        super(id);
    }
}
