package org.thingsboard.server.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.thingsboard.rule.engine.api.msg.DeviceNameOrTypeUpdateMsg;
import org.thingsboard.rule.engine.api.msg.OrganizationNameOrUpdateMsg;
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

    //thêm mới thành công kèm token
    @PreAuthorize("hasAnyAuthority('TENANT_ADMIN', 'CUSTOMER_USER')")
    @RequestMapping(value = "/camera", method = RequestMethod.POST)
    @ResponseBody
    public Organization saveDevice(@RequestBody Device device, @RequestParam (name = "accessToken", required = false) String accessToken) throws ThingsboardException {
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
                organizationService.saveCameraWithAccessToken(camera, accessToken);
//                organizationService.saveCamera(camera);
            }
            return camera;
        } catch (Exception e) {
            logEntityAction(emptyId(EntityType.DEVICE), camera, null, camera.getId() == null ? ActionType.ADDED : ActionType.UPDATED, e);
            throw handleException(e);
        }
    }


    //successful
    /*@PreAuthorize("hasAnyAuthority('TENANT_ADMIN', 'CUSTOMER_USER')")
    @RequestMapping(value = "/camera", method = RequestMethod.POST)
    @ResponseBody
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
            logEntityAction(emptyId(EntityType.DEVICE), camera, null, camera.getId() == null ? ActionType.ADDED : ActionType.UPDATED, e);
            throw handleException(e);
        }
    }*/
}
