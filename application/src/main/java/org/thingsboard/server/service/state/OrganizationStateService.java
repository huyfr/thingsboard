package org.thingsboard.server.service.state;

import org.springframework.context.ApplicationListener;
import org.thingsboard.server.common.data.Organization;
import org.thingsboard.server.queue.discovery.PartitionChangeEvent;

public interface OrganizationStateService extends ApplicationListener<PartitionChangeEvent> {

    void onCameraAdded(Organization camera);

    void onCameraUpdated(Organization camera);
}
