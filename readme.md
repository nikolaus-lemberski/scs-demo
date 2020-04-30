# SCS Demo

Demo for Service Registy, Client side load balancing (Spring Boot, Feign Client), internal routing (C2C networking), canary releases of new versions on Tanzu Application Services.

**This is a demo project. Do not use in production.**

## How to run the demo

1. Adjust the public route of frontend-service in `manifest.yml` files to your TAS / Cloud Foundry installation
2. Build the jars with maven `mvn clean install`
3. Create a space `cf create-space -s demo1`
4. Switch to space `cf target -s demo1`
5. Create service registry from marketplace `cf create-service p.service-registry standard registry`
6. Install frontend-service and backend-service `cf push`
7. Add network policy for interal routing `cf add-network-policy frontend-service --destination-app backend-service --protocol tcp --port 8080`
8. Call frontend-service on endpoint /backend to get a message from backend. If something goes wrong and circuit breaker takes over, check logs and service registry dashboard.
9. Play around with it, stop backend-service to see circuit breaker in action, scale backend-service up to see client side load balancing in action.
10. Install new version of backend-service; go into backend-service dir and run `cf push -f manifest-v2.yml`
11. Check frontend-service and service registry dashboard: frontend-service cannot access backend-service-v2, but backend-service-v2 is registered at service registry
12. Add network policy `cf add-network-policy frontend-service --destination-app backend-service-v2 --protocol tcp --port 8080`
13. frontend-service should now load balance between services v1 and v2 (canary release done). Via scaling up and down different canary deployement strategies can be implemented.
14. Create new space `cf create-space demo2`
15. Stretch registry to second space via service instance sharing `cf share-service registry -s demo2`
16. Go into second space `cf target -s demo2`
17. Install backend-service v3 in space demo2 `cf push -f manifest-v3.yml`
18. Check service registry, backend-service-v3 should be registered, frontend-service cannot reach backend-service-v3 (network policy missing).
19. Switch to space demo1 `cf target -s demo1`
20. Add network policy `cf add-network-policy frontend-service --destination-app backend-service-v3 --protocol tcp --port 8080 -s demo2`

Now you have explored how to do canary releases, client side load balancing, service registry and working with different spaces.

**If your clients cannot handle other services that are not available (connection refused because network policy is missing), you can start new versions with status "out of service" at Service Registry**, then test the new version, then add network policy, then change the status of the new version to "up". See application.yml of backend-service how to set initial status to "out of service".
