package org.thingsboard.server.common.data.security.organization;

public interface OrganizationCredentialsFilter {
    String getCredentialsId();

    OrganizationCredentialsType getCredentialsType();
}
