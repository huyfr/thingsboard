package org.thingsboard.server.common.data.security.organization;

public class OrganizationX509Credentials implements OrganizationCredentialsFilter {

    private final String sha3Hash;

    public OrganizationX509Credentials(String sha3Hash) {
        this.sha3Hash = sha3Hash;
    }

    @Override
    public String getCredentialsId() { return sha3Hash; }

    @Override
    public OrganizationCredentialsType getCredentialsType() {
        return OrganizationCredentialsType.X509_CERTIFICATE;
    }

    @Override
    public String toString() {
        return "OrganizationX509Credentials [SHA3=" + sha3Hash + "]";
    }
}
