# Implementation of Packlink Engineering coding test

## Instructions

1. Open the project in your favorite IDE (e.q. Intellij Idea)
2. Run spring-boot `Application` class of `parcels-service-ws` module. The service will be running on port `8085`
3. Register a `shipment` by calling POST `http://localhost:8085/api/register`
4. Push few `tracking` objects by calling PUT `http://localhost:8085/api/push` 
5. Alternatively, you can run the example using http client. Open the class `ParcelsServiceClientUsage` of `parcels-service-client` module and run the `main` method.
6. You should see a message in logs like: `Received event: TrackingEvent(reference=EFGH123456, status=NOT_FOUND)` for each resolved `tracking`
7. Send a feedback to the author


