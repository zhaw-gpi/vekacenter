# VeKa-Center-Austauschdienst (veka-center-auskunftsdienst)

> Autoren der Dokumentation: Björn Scheppler

> Dokumentation letztmals aktualisiert: 29.10.2018

Dieses Projekt simuliert den VeKa (Versichertenkarten)-Center-Austauschdienst, welcher unter anderem für die [Umzugsplattform](https://github.com/zhaw-gpi/eumzug-plattform-2018) benötigt wird.

## (Technische) Komponenten des Austauschdienstes
1. **Spring Boot Starter** beinhaltend:
    1. Spring Boot-Standardkonfiguration mit Tomcat als Applikations- und Webserver
    2. Web-Komponenten von Spring
    3. Main-Methode in VeKaCenterAuskunftsdienstApplication-Klasse
2. **Persistierungs-Komponenten**:
    1. H2-Datenbank-Unterstützung inklusive Console-Servlet über ApplicationConfiguration-Klasse
    3. Java Persistence API (JPA)
    4. Package Repositories und Package entities für Address, Card, Insurer und Person
    6. initialData.sql in test/ressources, um die Datenbank initial mit sinnvollen Stammdaten zu füllen
3. **REST-Komponenten**:
    1. Spring Boot Web-Komponenten für das Anbieten von REST-Services
    2. RESTController-Klassen für das RequestMapping von Ressourcen:
        1. CardRestController: Zugriff auf Methoden im Zusammenhang mit Versichertenkarten (momentan ausgeben aller Karten sowie einer Karte basierend auf der Versicherten-Kartennummer)
        2. InsurerRestController: Zugriff auf Methoden im Zusammenhang mit Versichereren (momentan ausgeben aller versicherer, eines Versicherers basierend auf BAG-Nummer sowie hinzufügen eines neuen Versicherers)
5. **Frontend-Komponenten**:
    1. Statische Welcome-Webseite index.html
    2. Dynamische Webseite mit einer Liste aller registrierten Versicherern, basierend auf:
        1. Thymeleaf-Templating Engine
        2. ThymeLeaf-Template InsurerListTemplate
        3. InsurerWebController, welcher dieses Template mit einem Model füttert und das Mapping auf den Request definiert
6. "Sinnvolle" **Grundkonfiguration** in application.properties für Tomcat, Datenbank und Datumsformat
7. **soapUI-Projekt** in src\test\resources\VeKa-REST-API-soapui-project.xml, um die REST-Abfragen bequem ausführen zu können.

## Deployment
1. **Erstmalig** oder bei Problemen ein **Clean & Build (Netbeans)**, respektive `mvn clean install` (Cmd) durchführen
2. Bei Änderungen am POM-File oder bei **(Neu)kompilierungsbedarf** genügt ein **Build (Netbeans)**, respektive `mvn install`
3. Für den **Start** ist ein **Run (Netbeans)**, respektive `java -jar .\target\NAME DES JAR-FILES.jar` (Cmd) erforderlich. Dabei wird Tomcat gestartet, die Datenbank erstellt/hochgefahren mit den Eigenschaften (application.properties) und die verschiedenen Resourcen-URL-Mappings vorgenommen.
4. Initial sind **Daten in der Datenbank anzulegen**. Hierzu an der Console anmelden (http://localhost:8070/console; jdbc:h2:./veka, Benutzer sa, kein Passwort) und die SQL-Befehle in test/resources/initalData.sql ausführen.
5. Das **Beenden** geschieht mit **Stop Build/Run (Netbeans)**, respektive **CTRL+C** (Cmd)

## Nutzung (Testing) der Applikation
### Frontend
1. http://localhost:8070 aufrufen => Eine Willkommens-Nachricht wird angezeigt.
2. http://localhost:8070/insurerer aufrufen => Eine Liste aller Versicherer wird angezeigt.

### REST-Aufrufe
Diese können am einfachsten mit soapUI und dem entsprechenden Testprojekt ausgeführt werden.
Die verfügbaren URLs sind aber auch unter http://localhost:8070 aufgeführt.