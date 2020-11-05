package org.thingsboard.server.dao.organization;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thingsboard.server.common.data.*;
import org.thingsboard.server.common.data.id.CustomerId;
import org.thingsboard.server.common.data.id.OrganizationId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.security.DeviceCredentials;
import org.thingsboard.server.common.data.security.organization.OrganizationCredentials;
import org.thingsboard.server.common.data.security.organization.OrganizationCredentialsType;
import org.thingsboard.server.dao.customer.CustomerDao;
import org.thingsboard.server.dao.entity.AbstractEntityService;
import org.thingsboard.server.dao.entityview.EntityViewService;
import org.thingsboard.server.dao.exception.DataValidationException;
import org.thingsboard.server.dao.service.DataValidator;
import org.thingsboard.server.dao.tenant.TenantDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.thingsboard.server.common.data.CacheConstants.CAMERA_CACHE;
import static org.thingsboard.server.common.data.CacheConstants.DEVICE_CACHE;
import static org.thingsboard.server.dao.device.DeviceServiceImpl.INCORRECT_DEVICE_ID;
import static org.thingsboard.server.dao.model.ModelConstants.NULL_UUID;
import static org.thingsboard.server.dao.service.Validator.validateId;

@Service
@Slf4j
public class OrganizationServiceImpl extends AbstractEntityService implements OrganizationService {

    @Autowired
    private OrganizationDao organizationDao;

    @Autowired
    private EntityViewService entityViewService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private TenantDao tenantDao;

    @Autowired
    private CustomerDao customerDao;

    @Override
    public void deleteCamera(TenantId tenantId, OrganizationId cameraId) {
        log.trace("Executing deleteDevice [{}]", cameraId);
        validateId(cameraId, INCORRECT_DEVICE_ID + cameraId);

        Organization camera = organizationDao.findById(tenantId, cameraId.getId());
        try {
            List<EntityView> entityViews = entityViewService.findEntityViewsByTenantIdAndEntityIdAsync(camera.getTenantId(), cameraId).get();
            if (entityViews != null && !entityViews.isEmpty()) {
                throw new DataValidationException("Can't delete device that has entity views!");
            }
        } catch (ExecutionException | InterruptedException e) {
            log.error("Exception while finding entity views for deviceId [{}]", cameraId, e);
            throw new RuntimeException("Exception while finding entity views for deviceId [" + cameraId + "]", e);
        }
        deleteEntityRelations(tenantId, cameraId);

        List<Object> list = new ArrayList<>();
        list.add(camera.getTenantId());
        list.add(camera.getName());
        Cache cache = cacheManager.getCache(CAMERA_CACHE);
        cache.evict(list);

        organizationDao.removeById(tenantId, cameraId.getId());
    }

    @Override
    public Organization saveCamera(Organization camera) {
        return doSaveCamera(camera);
    }

    private Organization doSaveCamera(Organization camera) {
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
