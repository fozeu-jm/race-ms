package fr.pmu.gateways.event;

import fr.pmu.domain.entity.Race;

public interface EventBusGateway {
    void sendCreateRaceMessage(Race race, String topicName);
}
