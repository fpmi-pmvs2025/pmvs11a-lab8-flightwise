# File structure

## Repository

The repository includes commit linting with `husky` and requires several npm packages that are located in package.json. Github actions and PR templates are set up in `.github` folder. Documentation is located in `docs` folder. Application folder is named `Flightwise`.

```
├── .github
├── .husky
├── Flightwise 
├── docs
├── README.md
├── commitlint.config.js
├── package-lock.json
└── package.json
```

## Application
The application structure is provided below as basic Android project.

`app/`

build.gradle.kts - Gradle configuration for the app module.

proguard-rules.pro - ProGuard configuration for code shrinking and obfuscation.

`src/`

androidTest/ - Instrumentation UI tests.

Path: src/androidTest/java/by/bsu/flightwise

Contains UI tests for major screens such as Login, Main, Register, Payment, etc.

Test files follow the pattern: ActivityNameUITest.kt

main/ - Main application source code.

Path: src/main/java/by/bsu/flightwise

`assets/`

flightwise.db - Preloaded SQLite database used in the application.

`data/`

Contains application data logic:

dao/ - DAO interfaces for Room database operations.

dao/impl/ - Implementations for DAOs.

database/DatabaseHelper.kt - Centralized helper for managing the database.

entity/ - Kotlin data classes representing database tables.

service/

SetupDatabase.kt - Handles initial setup and seeding of the database.

`ui/`

activities/ - Kotlin files for app screens.

fragments/ - Reusable UI fragments (e.g., Header, Footer, DateInput).

theme/ - App theming (colors, typography).

`res/`

Drawable assets, fonts, and app launcher icons.

values/ and values-ru/ - App string resources in English and Russian.

xml/ - Backup and data extraction configurations.

`test/`

Unit test directory.

`Gradle and Wrapper Files`

build.gradle.kts - Top-level Gradle build file.

gradle/ - Contains version catalogs and wrapper files.

gradle.properties, settings.gradle.kts - Gradle settings.

gradlew, gradlew.bat - Gradle wrapper scripts.

```
│   ├── app
│   │   ├── build.gradle.kts
│   │   ├── proguard-rules.pro
│   │   └── src
│   │       ├── androidTest
│   │       │   └── java
│   │       │       └── by
│   │       │           └── bsu
│   │       │               └── flightwise
│   │       │                   ├── AboutActivityUITest.kt
│   │       │                   ├── ExampleInstrumentedTest.kt
│   │       │                   ├── LoginActivityUITest.kt
│   │       │                   ├── MainActivityUITest.kt
│   │       │                   ├── PaymentActivityUITest.kt
│   │       │                   ├── RegisterActivityUITest.kt
│   │       │                   ├── SearchTicketsUITest.kt
│   │       │                   ├── TicketActivityUITest.kt
│   │       │                   └── TicketsActivityUITest.kt
|   |       |                    ...
│   │       ├── main
│   │       │   ├── AndroidManifest.xml
│   │       │   ├── assets
│   │       │   │   └── flightwise.db
│   │       │   ├── ic_launcher-playstore.png
│   │       │   ├── java
│   │       │   │   └── by
│   │       │   │       └── bsu
│   │       │   │           └── flightwise
│   │       │   │               ├── data
│   │       │   │               │   ├── dao
│   │       │   │               │   │   ├── AirplaneDao.kt
│   │       │   │               │   │   ├── AirportDao.kt
│   │       │   │               │   │   ├── FlightDao.kt
│   │       │   │               │   │   ├── PassengerDao.kt
│   │       │   │               │   │   ├── PaymentDao.kt
│   │       │   │               │   │   ├── PricingRuleDao.kt
│   │       │   │               │   │   ├── TicketDao.kt
│   │       │   │               │   │   ├── UserDao.kt
│   │       │   │               │   │   └── impl
│   │       │   │               │   │       ├── AirplaneDaoImpl.kt
│   │       │   │               │   │       ├── AirportDaoImpl.kt
│   │       │   │               │   │       ├── FlightDaoImpl.kt
│   │       │   │               │   │       ├── PassengerDaoImpl.kt
│   │       │   │               │   │       ├── PaymentDaoImpl.kt
│   │       │   │               │   │       ├── PricingRuleDaoImpl.kt
│   │       │   │               │   │       ├── TicketDaoImpl.kt
│   │       │   │               │   │       └── UserDaoImpl.kt
│   │       │   │               │   ├── database
│   │       │   │               │   │   └── DatabaseHelper.kt
│   │       │   │               │   └── entity
│   │       │   │               │       ├── Airplane.kt
│   │       │   │               │       ├── Airport.kt
│   │       │   │               │       ├── Flight.kt
│   │       │   │               │       ├── Passenger.kt
│   │       │   │               │       ├── Payment.kt
│   │       │   │               │       ├── PricingRule.kt
│   │       │   │               │       ├── Ticket.kt
│   │       │   │               │       └── User.kt
│   │       │   │               ├── service
│   │       │   │               │   └── SetupDatabase.kt
│   │       │   │               └── ui
│   │       │   │                   ├── activities
│   │       │   │                   │   ├── AboutActivity.kt
│   │       │   │                   │   ├── LoginActivity.kt
│   │       │   │                   │   ├── MainActivity.kt
│   │       │   │                   │   ├── PaymentActivity.kt
│   │       │   │                   │   ├── RegisterActivity.kt
│   │       │   │                   │   ├── SearchTicketsActivity.kt
│   │       │   │                   │   ├── TicketActivity.kt
│   │       │   │                   │   └── TicketsActivity.kt
│   │       │   │                   ├── fragments
│   │       │   │                   │   ├── DateInputFragment.kt
│   │       │   │                   │   ├── FooterFragment.kt
│   │       │   │                   │   ├── HeaderFragment.kt
│   │       │   │                   │   └── TicketFragment.kt
│   │       │   │                   └── theme
│   │       │   │                       ├── Color.kt
│   │       │   │                       ├── Theme.kt
│   │       │   │                       └── Type.kt
│   │       │   └── res
│   │       │       ├── drawable
│   │       │       │   ├── airplane.png
│   │       │       │   ├── ic_launcher_background.xml
│   │       │       │   ├── ic_launcher_foreground.xml
│   │       │       │   ├── luggage.png
│   │       │       │   └── main_activity_header.png
│   │       │       ├── font
│   │       │       │   ├── inter_bold.ttf
│   │       │       │   ├── inter_medium.ttf
│   │       │       │   ├── inter_regular.ttf
│   │       │       │   └── inter_semibold.ttf
│   │       │       ├── mipmap-anydpi-v26
│   │       │       │   ├── ic_launcher.xml
│   │       │       │   └── ic_launcher_round.xml
│   │       │       ├── mipmap-hdpi
│   │       │       │   ├── ic_launcher.webp
│   │       │       │   ├── ic_launcher_foreground.webp
│   │       │       │   └── ic_launcher_round.webp
│   │       │       ├── mipmap-mdpi
│   │       │       │   ├── ic_launcher.webp
│   │       │       │   ├── ic_launcher_foreground.webp
│   │       │       │   └── ic_launcher_round.webp
│   │       │       ├── mipmap-xhdpi
│   │       │       │   ├── ic_launcher.webp
│   │       │       │   ├── ic_launcher_foreground.webp
│   │       │       │   └── ic_launcher_round.webp
│   │       │       ├── mipmap-xxhdpi
│   │       │       │   ├── ic_launcher.webp
│   │       │       │   ├── ic_launcher_foreground.webp
│   │       │       │   └── ic_launcher_round.webp
│   │       │       ├── mipmap-xxxhdpi
│   │       │       │   ├── ic_launcher.webp
│   │       │       │   ├── ic_launcher_foreground.webp
│   │       │       │   └── ic_launcher_round.webp
│   │       │       ├── values
│   │       │       │   ├── colors.xml
│   │       │       │   ├── ic_launcher_background.xml
│   │       │       │   ├── strings.xml
│   │       │       │   └── themes.xml
│   │       │       ├── values-ru
│   │       │       │   └── strings.xml
│   │       │       └── xml
│   │       │           ├── backup_rules.xml
│   │       │           └── data_extraction_rules.xml
│   │       └── test
│   │           └── java
│   │               └── by
│   │                   └── bsu
│   │                       └── flightwise
│   │                           └── ExampleUnitTest.kt
│   ├── build.gradle.kts
│   ├── gradle
│   │   ├── libs.versions.toml
│   │   └── wrapper
│   │       ├── gradle-wrapper.jar
│   │       └── gradle-wrapper.properties
│   ├── gradle.properties
│   ├── gradlew
│   ├── gradlew.bat
│   └── settings.gradle.kts

```