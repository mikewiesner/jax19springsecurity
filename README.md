# Demo Project: Spring Security – sichere Self Contained Systems bauen #slideless

Die Anwendung besteht aus 2 Self Contained Systems welche auf Gradle basieren.

Vorraussetzungen:
- MongoDB https://www.mongodb.com/
- Keycloak https://www.keycloak.org/
- Lombok (falls in der IDE bearbeitet wird) https://projectlombok.org/

Installation auf OSX:

- MongoDB installieren und starten
```bash
> brew install mongodb
> mongod
```

- Keycloack installieren und starten
```bash
> curl -o keycloak.tar.gz -L https://downloads.jboss.org/keycloak/4.3.0.Final/keycloak-4.3.0.Final.tar.gz
> tar -xvzf keycloak.tar.gz
> cd keycloak-4.3.0.Final/bin
> ./standalone.sh
```

- Keycloack konfigurieren
    - Browser mit http://localhost:8080 öffnen
    - Admin-Benutzer erzeugen und anschließend auf `Administration Console` klicken.
    - Auf `Master` und anschließend auf `Add realm` klicken.
    - Auf `Import / Select file` klicken und anschließend `PU-realm.json` auswählen.
    - Mit `create` importieren.
    - Konfiguration für die Employee-Anwendung unter `Clients` öffnen und anschließend in der Zeile `employee` auf `Edit` klicken.
    - In dem Tab `Credentials`, `Regenerate Secret` auswählen und das neu erzeugt Secret in die Zwischenablage kopieren.
    - Kopiertes Secret in die Datei `employee/src/main/resources/application.yml` bei dem Key `client-secret` einfügen.
- **Keycloak ist nur für die Employee-Anwendung konfiguriert. Für die PTO-Anwendung muss eine analoge konfiguration erstellt werden.**

**Starten der Employee-Anwendung**
```bash
> cd employee
> ./gradlew bootRun
```
Zugriff via: http://localhost:8082

**Starten der PTO-Anwendung**
```bash
> cd pto
> ./gradlew bootRun
```
Zugriff via: http://localhost:8081

