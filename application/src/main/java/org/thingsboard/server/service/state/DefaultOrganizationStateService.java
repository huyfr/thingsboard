package org.thingsboard.server.service.state;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thingsboard.server.common.data.Organization;
import org.thingsboard.server.common.data.id.OrganizationId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.gen.transport.TransportProtos;
import org.thingsboard.server.queue.discovery.PartitionChangeEvent;
import org.thingsboard.server.queue.util.TbCoreComponent;
import org.thingsboard.server.service.queue.TbClusterService;

@Service
@TbCoreComponent
@Slf4j
public class DefaultOrganizationStateService implements OrganizationStateService {

    private final TbClusterService clusterService;

    public DefaultOrganizationStateService(TbClusterService clusterService) {
        this.clusterService = clusterService;
    }

    @Override
    public void onCameraAdded(Organization camera) {
        sendCameraEvent(camera.getTenantId(), camera.getId(), true, false, false);
    }

    @Override
    public void onCameraUpdated(Organization camera) {
        sendCameraEvent(camera.getTenantId(), camera.getId(), false, true, false);
    }

    @Override
    public void onApplicationEvent(PartitionChangeEvent partitionChangeEvent) {

    }

    private void sendCameraEvent(TenantId tenantId, OrganizationId organizationId, boolean added, boolean updated, boolean deleted) {
        TransportProtos.DeviceStateServiceMsgProto.Builder builder = TransportProtos.DeviceStateServiceMsgProto.newBuilder();
        builder.setTenantIdMSB(tenantId.getId().getMostSignificantBits());
        builder.setTenantIdLSB(tenantId.getId().getLeastSignificantBits());
        builder.setDeviceIdMSB(organizationId.getId().getMostSignificantBits());
        builder.setDeviceIdLSB(organizationId.getId().getLeastSignificantBits());
        builder.setAdded(added);
        builder.setUpdated(updated);
        builder.setDeleted(deleted);
        TransportProtos.DeviceStateServiceMsgProto msg = builder.build();
        clusterService.pushMsgToCore(tenantId, organizationId, TransportProtos.ToCoreMsg.newBuilder().setDeviceStateServiceMsg(msg).build(), null);
    }
}
