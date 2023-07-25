package fr.pmu.advice;

import fr.pmu.domain.entity.Race;
import fr.pmu.gateways.event.EventBusGateway;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PublishAdvice {
    @Value("${kafka.topic}")
    private String kafkaTopic;

    private final EventBusGateway eventBus;

    public PublishAdvice(EventBusGateway eventBus) {
        this.eventBus = eventBus;
    }

    @Pointcut("@annotation(fr.pmu.annotation.Produce)")
    public void publishAfterPointcut(){}

    @After("publishAfterPointcut()")
    public void logMethodCallsAfterAdvice(JoinPoint joinPoint) {
        Race race = (Race) joinPoint.getArgs()[0];
        eventBus.sendCreateRaceMessage(race, kafkaTopic);
    }
}
