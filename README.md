# WishList Project

## Overblik

**WishList** er et webapplikationsprojekt, der tillader brugere at oprette ønskesedler, dele dem med andre og reservere ønsker. Projektet er bygget med Java og Spring Framework til backend og Thymeleaf til frontend. Brugere kan oprette ønskesedler, tilføje ønsker (produkter), og dele dem med andre, der derefter kan reservere ønsker på en anonym måde.

---

## Funktionalitet

- **Oprettelse af ønskeseddel**: Brugere kan oprette ønskesedler og tilføje ønsker til dem.
- **Deling af ønskeseddel**: Ønskesedler kan deles med andre via et genereret unikt link, som giver adgang til en read-only version af ønskesedlen.
- **Reservation af ønsker**: Andre brugere kan reservere ønsker på den delte ønskeseddel uden at ejer kan se, hvem der har reserveret.
- **Backend**: Systemet bruger Java og Spring Framework til forretningslogik og databasehåndtering.
- **Frontend**: Thymeleaf bruges til at generere dynamiske HTML-sider og interagere med backend.

---

## Teknologier

- **Java 11** eller højere
- **Spring Boot** (Java web framework)
- **Thymeleaf** (Frontend templating engine)
- **MySQL** (Database)
- **Maven** (Byg og afhængighedsstyring)

---

## Installation

### Forudsætninger

- Java 11 eller højere
- Maven
- MySQL database

### Installationstrin

1. **Klon repository:**

   ```bash
   git clone https://github.com/yourusername/wishlist.git
   cd wishlist
2. **Opsæt database:**

    Opret en database i MySQL:

   ```bash
   CREATE DATABASE wishlistdatabase;

_Sørg for, at din application.properties-fil er korrekt konfigureret med databaseoplysninger:_

    spring.datasource.url=jdbc:mysql://localhost:3306/wishlistdatabase
    spring.datasource.username=root
    spring.datasource.password=yourpassword
    spring.jpa.hibernate.ddl-auto=update

**Byg og kør applikationen:**

Byg projektet med Maven:

`mvn clean install
`

Kør applikationen:

    mvn spring-boot:run

**Adgang til applikationen:** Applikationen vil være tilgængelig på http://localhost:8080/.

----
# Struktur

----

## Backend

1. Controller: Indeholder REST-API metoder for CRUD-operationer på ønskesedler og produkter.
2. Service: Indeholder forretningslogik for oprettelse af ønskesedler og reservering af produkter.
3. Repository: Håndterer databaseinteraktioner, f.eks. oprettelse af ønskesedler og produkter.
4. Model: Indeholder Java-klasser, der afspejler de data, vi arbejder med (f.eks. WishList, Item, SharedItem).
5. DTO: Indeholder java klasser, der fungere som data transfer

## Frontend

* Thymeleaf bruges til at generere HTML-sider, der interagerer med backend.
* Wishlist read-only view: Brugere kan se en ønskeseddel i read-only mode og reservere produkter, hvis de ikke allerede er reserveret.