package org.thingsboard.rule.engine.api.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.thingsboard.server.common.data.id.DeviceId;
import org.thingsboard.server.common.data.id.OrganizationId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.msg.MsgType;

@Data
@AllArgsConstructor
public class OrganizationNameOrUpdateMsg implements ToDeviceActorNotificationMsg {

    private final TenantId tenantId;
    private final OrganizationId organizationId;
    private final String deviceName;

    @Override
    public MsgType getMsgType() {
        return MsgType.DEVICE_NAME_OR_TYPE_UPDATE_TO_DEVICE_ACTOR_MSG;
    }

    @Override
    public DeviceId getDeviceId() {
        return null;
    }

    @Override
    public TenantId getTenantId() {
        return null;
    }
}
