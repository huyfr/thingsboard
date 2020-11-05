package org.thingsboard.server.common.data.security.organization;

public class OrganizationTokenCredentials implements OrganizationCredentialsFilter {

    private final String token;

    public OrganizationTokenCredentials(String token) {
        this.token = token;
    }

    @Override
    public String getCredentialsId() {
        return token;
    }

    @Override
    public OrganizationCredentialsType getCredentialsType() {
        return OrganizationCredentialsType.ACCESS_TOKEN;
    }

    @Override
    public String toString() {
        return "OrganizationTokenCredentials [token=" + token + "]";
    }

}
