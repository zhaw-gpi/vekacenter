# VeKa-Center-Austauschdienst (veka-center-auskunftsdienst)

> Autoren der Dokumentation: Björn Scheppler

> Dokumentation letztmals aktualisiert: 29.8.2018

Dieses Projekt simuliert den VeKa (Versichertenkarten)-Center-Austauschdienst, welcher unter anderem für die [Umzugsplattform](https://github.com/zhaw-gpi/eumzug-plattform-2018) benötigt wird.

## Inhaltsverzeichnis
  

### Wie können Studierende Bonus-Punkte sammeln (in Aufgabenstellung verschieben)
1. Weitere BusinessLogik, welche sie per REST anbieten (z.B. aktualisieren eines Versicherers und vor allem Aktualisieren der Adresse nach erfolgreicher Umzugsmeldung)
2. Weitere Frontend-Funktionalitäten

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
    2. RESTController-Klassen, welche die Resourcen auf entsprechende Request-URLs mappen:
        1. BaseInsuranceCardResourceDeclarations für alles im Zusammenhang mit Versichertenkarten
        2. InsurerResourceDeclarations für alles in Zusammenhang mit Versichereren
    3. CheckBaseInsuranceResult: Einfache Klasse, um eine komplexe Response liefern zu können
4. **Business-Logik**:
    1. CheckBaseInsuranceController: Controller-Klasse, welche prüft, ob eine Person grundversichert ist mithilfe der Persistierungs-Komponenten
    2. InsurerController mit zwei Methoden:
        1. getInsurerOfCard: Gibt ein Versicherer-Objekt zu einer bestimmten Kartennummer zurück
        2. addInsurer: Legt ein neues Versicherer-Objekt an
5. **Frontend-Komponenten**:
    1. Statische Welcome-Webseite index.html
    2. Dynamische Webseite mit einer Liste aller registrierten Versicherern, basierend auf:
        1. Thymeleaf-Templating Engine
        2. ThymeLeaf-Template InsurerListTemplate
        3. InsurerListController, welcher dieses Template mit einem Model füttert und das Mapping auf den Request definiert
6. "Sinnvolle" **Grundkonfiguration** in application.properties für Tomcat, Datenbank und Datumsformat

### Deployment
1. **Erstmalig** oder bei Problemen ein **Clean & Build (Netbeans)**, respektive `mvn clean install` (Cmd) durchführen
2. Bei Änderungen am POM-File oder bei **(Neu)kompilierungsbedarf** genügt ein **Build (Netbeans)**, respektive `mvn install`
3. Für den **Start** ist ein **Run (Netbeans)**, respektive `java -jar .\target\NAME DES JAR-FILES.jar` (Cmd) erforderlich. Dabei wird Tomcat gestartet, die Datenbank erstellt/hochgefahren mit den Eigenschaften (application.properties) und die verschiedenen Resourcen-URL-Mappings vorgenommen.
4. Initial sind **Daten in der Datenbank anzulegen**. Hierzu an der Console anmelden (http://localhost:8070/console; jdbc:h2:./veka, Benutzer sa, kein Passwort) und die SQL-Befehle in test/resources/initalData.sql ausführen.
5. Das **Beenden** geschieht mit **Stop Build/Run (Netbeans)**, respektive **CTRL+C** (Cmd)

## Nutzung (Testing) der Applikation
### Frontend
1. http://localhost:8070 aufrufen => Eine Willkommens-Nachricht wird angezeigt.
2. http://localhost:8070/registeredinsurerer aufrufen => Eine Liste aller Versicherer wird angezeigt.

### REST-Aufrufe
Diese können entweder direkt im Browser durchgeführt werden oder z.B. mit soapUI.
1. Einen neuen Versicherer anlegen:
    1. z.B. http://localhost:8070/vekaapi/addinsurer?name=Visana Services Bern&street=Laupenstrasse&housenumber=3&plz=3001&town=Bern
    2. Falls dieser Versicherer schon existiert, wird er zurückgegeben, ohne dass in der Datenbank etwas geändert wurde
    3. Existiert er nicht, wird er angelegt -> zu prüfen in der Tabelle INSURER in http://localhost:8070/console; jdbc:h2:./veka, Benutzer sa, kein Passwort
2. Den Versicherer zu einer Karte zurückgeben:
    1. z.B. http://localhost:8070/vekaapi/getinsurerofcard?baseInsuranceNumber=78847589268403592
    2. Weitere erfasste Kartennummern: 36026946698526862, 596947027238113990 und 743794085316616163
3. Prüfen, ob eine Person mit einer Karte grundversichert ist:
    1. z.B. http://localhost:8070/vekaapi/checkbaseinsurance?firstName=Anna&officialName=Meier&dateOfBirth=01.01.1982&baseInsuranceNumber=78847589268403592
    2. Diese Methode wird vor allem innerhalb von https://github.com/zhaw-gpi/eumzug-plattform-2018 genutzt, wo im README auch weitere Testdaten angegeben sind