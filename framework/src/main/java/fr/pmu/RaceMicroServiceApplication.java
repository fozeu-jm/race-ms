package fr.pmu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"fr.pmu.*"})
@EntityScan(basePackages = {"fr.pmu.gateways.mapper"})
@EnableJpaRepositories(basePackages = {"fr.pmu.repositories"})
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class RaceMicroServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RaceMicroServiceApplication.class, args);
    }
}
