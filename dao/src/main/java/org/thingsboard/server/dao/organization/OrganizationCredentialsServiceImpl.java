/*
package org.thingsboard.server.dao.organization;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thingsboard.server.common.data.Device;
import org.thingsboard.server.common.data.Organization;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.security.DeviceCredentials;
import org.thingsboard.server.common.data.security.organization.OrganizationCredentials;
import org.thingsboard.server.common.data.security.organization.OrganizationCredentialsType;
import org.thingsboard.server.common.msg.EncryptionUtil;
import org.thingsboard.server.dao.entity.AbstractEntityService;
import org.thingsboard.server.dao.exception.DataValidationException;
import org.thingsboard.server.dao.service.DataValidator;

@Service
@Slf4j
public class OrganizationCredentialsServiceImpl extends AbstractEntityService implements OrganizationCredentialsService {

    @Override
    public OrganizationCredentials createCameraCredentials(TenantId tenantId, OrganizationCredentials organizationCredentials) {
        return null;
    }

    private OrganizationCredentials saveOrUpdate(TenantId tenantId, OrganizationCredentials organizationCredentials) {
        if (organizationCredentials.getCredentialsType() == OrganizationCredentialsType.X509_CERTIFICATE) {
            formatCertData(organizationCredentials);
        }
        log.trace("Executing updateDeviceCredentials [{}]", organizationCredentials);
        credentialsValidator.validate(organizationCredentials, id -> tenantId);
        try {
            return deviceCredentialsDao.save(tenantId, organizationCredentials);
        } catch (Exception t) {
            ConstraintViolationException e = extractConstraintViolationException(t).orElse(null);
            if (e != null && e.getConstraintName() != null
                    && (e.getConstraintName().equalsIgnoreCase("device_credentials_id_unq_key") || e.getConstraintName().equalsIgnoreCase("device_credentials_device_id_unq_key"))) {
                throw new DataValidationException("Specified credentials are already registered!");
            } else {
                throw t;
            }
        }
    }

    private void formatCertData(OrganizationCredentials organizationCredentials) {
        String cert = EncryptionUtil.trimNewLines(organizationCredentials.getCredentialsValue());
        String sha3Hash = EncryptionUtil.getSha3Hash(cert);
        organizationCredentials.setCredentialsId(sha3Hash);
        organizationCredentials.setCredentialsValue(cert);
    }

    private DataValidator<OrganizationCredentials> credentialsValidator = new DataValidator<OrganizationCredentials>() {

        @Override
        protected void validateCreate(TenantId tenantId, DeviceCredentials deviceCredentials) {
            if (deviceCredentialsDao.findByDeviceId(tenantId, deviceCredentials.getDeviceId().getId()) != null) {
                throw new DataValidationException("Credentials for this device are already specified!");
            }
            if (deviceCredentialsDao.findByCredentialsId(tenantId, deviceCredentials.getCredentialsId()) != null) {
                throw new DataValidationException("Device credentials are already assigned to another device!");
            }
        }

        @Override
        protected void validateUpdate(TenantId tenantId, DeviceCredentials deviceCredentials) {
            if (deviceCredentialsDao.findById(tenantId, deviceCredentials.getUuidId()) == null) {
                throw new DataValidationException("Unable to update non-existent device credentials!");
            }
            DeviceCredentials existingCredentials = deviceCredentialsDao.findByCredentialsId(tenantId, deviceCredentials.getCredentialsId());
            if (existingCredentials != null && !existingCredentials.getId().equals(deviceCredentials.getId())) {
                throw new DataValidationException("Device credentials are already assigned to another device!");
            }
        }

        @Override
        protected void validateDataImpl(TenantId tenantId, DeviceCredentials deviceCredentials) {
            if (deviceCredentials.getDeviceId() == null) {
                throw new DataValidationException("Device credentials should be assigned to device!");
            }
            if (deviceCredentials.getCredentialsType() == null) {
                throw new DataValidationException("Device credentials type should be specified!");
            }
            if (StringUtils.isEmpty(deviceCredentials.getCredentialsId())) {
                throw new DataValidationException("Device credentials id should be specified!");
            }
            Device device = deviceService.findDeviceById(tenantId, deviceCredentials.getDeviceId());
            if (device == null) {
                throw new DataValidationException("Can't assign device credentials to non-existent device!");
            }
        }
    };
}
*/
