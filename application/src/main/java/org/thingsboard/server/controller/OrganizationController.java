package org.thingsboard.server.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.thingsboard.server.common.data.Device;
import org.thingsboard.server.common.data.EntityType;
import org.thingsboard.server.common.data.Organization;
import org.thingsboard.server.common.data.audit.ActionType;
import org.thingsboard.server.common.data.exception.ThingsboardException;
import org.thingsboard.server.queue.util.TbCoreComponent;
import org.thingsboard.server.service.security.permission.Resource;

@RestController
@TbCoreComponent
@RequestMapping("/api")
public class OrganizationController extends BaseController {

    //thêm mới camera
    @PreAuthorize("hasAnyAuthority('TENANT_ADMIN', 'CUSTOMER_USER')")
    @RequestMapping(value = "/camera", method = RequestMethod.POST)
    @ResponseBody
    /*public Organization saveCamera(@RequestBody Organization camera, @RequestParam(name = "accessToken", required = false) String accessToken) throws ThingsboardException {
        Organization savedCamera;
        try {
            camera.setTenantId(getCurrentUser().getTenantId()); //true
            checkEntity(camera.getId(), camera, Resource.CAMERA); //true
            savedCamera = checkNotNull(organizationService.saveCameraWithAccessToken(camera, accessToken));
            tbClusterService.pushMsgToCore(new OrganizationNameOrUpdateMsg(savedCamera.getTenantId(), savedCamera.getId(), savedCamera.getName()), null);
            logEntityAction(savedCamera.getId(), savedCamera, savedCamera.getCustomerId(), savedCamera.getId() == null ? ActionType.ADDED : ActionType.UPDATED, null);
            if (camera.getId() == null) {
                organizationStateService.onCameraAdded(camera);
            } else {
                organizationStateService.onCameraUpdated(camera);
            }
            return camera;
        } catch (Exception e) {
            logEntityAction(emptyId(EntityType.CAMERA), camera, null, camera.getId() == null ? ActionType.ADDED : ActionType.UPDATED, e);
            throw handleException(e);
        }
    }*/

    public Organization saveDevice(@RequestBody Device device) throws ThingsboardException {
        Organization camera = new Organization();
        try {
            device.setTenantId(getCurrentUser().getTenantId());
            checkEntity(device.getId(), device, Resource.DEVICE);
            if (device.getId() == null) {
                camera.setName(device.getName());
                camera.setTenantId(device.getTenantId());
                camera.setCustomerId(device.getCustomerId());
                camera.setAdditionalInfo(device.getAdditionalInfo());
                camera.setCreatedTime(device.getCreatedTime());
                camera.setUrl(device.getLabel());
                organizationService.saveCamera(camera);
            }
            return camera;
        } catch (Exception e) {
            logEntityAction(emptyId(EntityType.DEVICE), device, null, device.getId() == null ? ActionType.ADDED : ActionType.UPDATED, e);
            throw handleException(e);
        }
    }
}
