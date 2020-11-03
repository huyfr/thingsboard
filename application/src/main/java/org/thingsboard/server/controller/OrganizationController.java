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

    //thêm mới camera
    @PreAuthorize("hasAnyAuthority('TENANT_ADMIN', 'CUSTOMER_USER')")
    @RequestMapping(value = "/camera", method = RequestMethod.POST)
    @ResponseBody
    public Organization saveCamera(@RequestBody Organization camera, @RequestParam(name = "accessToken", required = false) String accessToken) throws ThingsboardException {
        Organization savedCamera = null;
        try {
            camera.setTenantId(getCurrentUser().getTenantId());
            checkEntity(camera.getId(), camera, Resource.CAMERA);
            savedCamera = checkNotNull(organizationService.saveCameraWithAccessToken(camera, accessToken));
            tbClusterService.pushMsgToCore(new OrganizationNameOrUpdateMsg(savedCamera.getTenantId(), savedCamera.getId(), savedCamera.getName()), null);
            logEntityAction(savedCamera.getId(), savedCamera, savedCamera.getCustomerId(), savedCamera.getId() == null ? ActionType.ADDED : ActionType.UPDATED, null);
            if (camera.getId() == null) {
                organizationStateService.onCameraAdded(savedCamera);
            } else {
                organizationStateService.onCameraUpdated(savedCamera);
            }
//            return savedCamera;
        } catch (Exception e) {
            /*logEntityAction(emptyId(EntityType.DEVICE), device, null, device.getId() == null ? ActionType.ADDED : ActionType.UPDATED, e);
            throw handleException(e);*/
            System.out.println(e.getMessage());
        }
        return savedCamera;
    }
}
