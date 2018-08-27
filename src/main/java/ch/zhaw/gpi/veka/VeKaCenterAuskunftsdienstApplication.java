package ch.zhaw.gpi.veka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hauptklasse für die VeKa-Center-Auskunftsdienst-Applikation
 * 
 * Die Applikation ist stark angelehnt an das Getting Started-Beispiel hier:
 * https://spring.io/guides/gs/rest-service/ und auch an https://spring.io/guides/gs/serving-web-content/
 * 
 * @SpringBootApplication stellt sicher, dass diese Klasse die SpringBoot-Applikation automatisch konfiguriert und vieles mehr. Details: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-using-springbootapplication-annotation
 * 
 * @author scep
 */
@SpringBootApplication
public class VeKaCenterAuskunftsdienstApplication {
    public static void main(String[] args) {
        SpringApplication.run(VeKaCenterAuskunftsdienstApplication.class, args);
    }
}
