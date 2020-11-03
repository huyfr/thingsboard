package org.thingsboard.server.dao.organization;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thingsboard.server.common.data.Customer;
import org.thingsboard.server.common.data.Device;
import org.thingsboard.server.common.data.Organization;
import org.thingsboard.server.common.data.Tenant;
import org.thingsboard.server.common.data.id.CustomerId;
import org.thingsboard.server.common.data.id.OrganizationId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.security.organization.OrganizationCredentials;
import org.thingsboard.server.common.data.security.organization.OrganizationCredentialsType;
import org.thingsboard.server.dao.customer.CustomerDao;
import org.thingsboard.server.dao.entity.AbstractEntityService;
import org.thingsboard.server.dao.exception.DataValidationException;
import org.thingsboard.server.dao.service.DataValidator;
import org.thingsboard.server.dao.tenant.TenantDao;

import static org.thingsboard.server.common.data.CacheConstants.CAMERA_CACHE;
import static org.thingsboard.server.dao.model.ModelConstants.NULL_UUID;

@Service
@Slf4j
public class OrganizationServiceImpl extends AbstractEntityService implements OrganizationService {

    @Autowired
    private OrganizationDao organizationDao;

    @Autowired
    private TenantDao tenantDao;

    @Autowired
    private CustomerDao customerDao;

    @CacheEvict(cacheNames = CAMERA_CACHE, key = "{#camera.tenantId, #camera.name}")
    @Override
    public Organization saveCameraWithAccessToken(Organization camera, String accessToken) {
        return doSaveCamera(camera, accessToken);
    }

    private Organization doSaveCamera(Organization camera, String accessToken) {
        log.trace("vào hàm doSaveCamera");
        cameraValidator.validate(camera, Organization::getTenantId);
        Organization savedCamera;
        try {
            savedCamera = organizationDao.save(camera.getTenantId(), camera);
            log.info(savedCamera.toString());
        } catch (Exception ex) {
            ConstraintViolationException e = extractConstraintViolationException(ex).orElse(null);
            if (e != null && e.getConstraintName() != null && e.getConstraintName().equalsIgnoreCase("device_name_unq_key")) {
                throw new DataValidationException("Camera with such name already exists!");
            } else {
                throw ex;
            }
        }
        if (camera.getId() == null) {
            OrganizationCredentials organizationCredentials = new OrganizationCredentials();
            organizationCredentials.setOrganizationId(new OrganizationId(savedCamera.getUuidId()));
            organizationCredentials.setOrganizationCredentialsType(OrganizationCredentialsType.ACCESS_TOKEN);
            organizationCredentials.setCredentialsId(!StringUtils.isEmpty(accessToken) ? accessToken : RandomStringUtils.randomAlphanumeric(20));
            //chưa xong
        }
        return savedCamera;
    }

    private DataValidator<Organization> cameraValidator = new DataValidator<Organization>() {

        @Override
        protected void validateCreate(TenantId tenantId, Organization camera) {
        }

        @Override
        protected void validateUpdate(TenantId tenantId, Organization camera) {
        }

        @Override
        protected void validateDataImpl(TenantId tenantId, Organization camera) {
            if (StringUtils.isEmpty(camera.getName())) {
                throw new DataValidationException("Camera name should be specified!");
            }
            if (camera.getTenantId() == null) {
                throw new DataValidationException("Camera should be assigned to tenant!");
            } else {
                Tenant tenant = tenantDao.findById(camera.getTenantId(), camera.getTenantId().getId());
                if (tenant == null) {
                    throw new DataValidationException("Device is referencing to non-existent tenant!");
                }
            }
            if (camera.getCustomerId() == null) {
                camera.setCustomerId(new CustomerId(NULL_UUID));
            } else if (!camera.getCustomerId().getId().equals(NULL_UUID)) {
                Customer customer = customerDao.findById(camera.getTenantId(), camera.getCustomerId().getId());
                if (customer == null) {
                    throw new DataValidationException("Can't assign camera to non-existent customer!");
                }
                if (!customer.getTenantId().getId().equals(camera.getTenantId().getId())) {
                    throw new DataValidationException("Can't assign camera to customer from different tenant!");
                }
            }
        }
    };
}
