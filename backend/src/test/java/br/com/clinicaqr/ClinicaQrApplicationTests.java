package br.com.clinicaqr;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties={"spring.datasource.url=jdbc:h2:mem:test;MODE=PostgreSQL","spring.datasource.driver-class-name=org.h2.Driver","spring.jpa.hibernate.ddl-auto=create-drop","spring.flyway.enabled=false"})
class ClinicaQrApplicationTests {
    @Test void contextLoads() {}
}