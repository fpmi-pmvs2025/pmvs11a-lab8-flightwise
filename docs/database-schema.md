# Database schema

### Entities
- **Airplanes** (model, capacity)
- **Airports** (unique code, name, city and country)
- **Flights** (unites information about departure and arrival places and dates)
- **Users** (authenticated users if the application)
- **Passengers** (owners of tickets bought for certain flights, can be added by authenticated users)
- **Pricing rules** (includes options of class or luggage availability)
- **Payments** (activates the ticket, can be payed by authenticated user)
- **Tickets** (unites information about passenger, flight and payment) 

![db](images/db_schema.png)

## Tables

### Airplanes
![airplanes](images/airplanes.png)

### Airports
![airports](images/airports.png)

### Flights
![flights](images/flights.png)

### Users
![users](images/users.png)

### Passengers
![passengers](images/passengers.png)

### Pricing rules
![pricing rules](images/pricing_rules.png)

### Payments
![payments](images/payments.png)

### Tickets
![tickets](images/tickets.png)

## Relationships

`Airplanes` -> one to one -> `Flights`

`Airports` -> one to one -> `Flights`

`Flights` -> one to many -> `Tickets`

`Tickets` -> many to one -> `Payments`

`Tickets` -> one to one -> `Passengers`

`Tickets` -> many to one -> `Pricing rules`

`Payments` -> many to one -> `Users`
