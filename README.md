# TUS-26-MA-CA2-Distributed-Microservice-App
## Microservices Architecture CA2
### Technological University of the Shannon

Microservices Architecture CA2: Cloud-native distributed application composed of interacting microservices

--- 

### Dependencies

#### Service Discovery & Routing

- **Eureka Discovery Client**: To allow the service to register with the discovery server dynamically.
- **Spring Cloud Config (Client)**: To fetch configuration from your central Config Server at startup.

#### Interaction & Resilience

- **OpenFeign**: (Optional) Makes service-to-service calls between Service A (Guitar-Store-Order) and Service B (Guitar-Store-Inventory) look like simple method calls.
- **Resilience4j**: For implementing Circuit Breakers and Fallbacks.

#### Security & Observability

- **Spring Boot Starter Security**: For authenticaion.
- **OAuth2 Resource Server**: JWT-based security.
- **Micrometer Tracing + Zipkin/Brave**: Trace single request across service boundaries.

