# SCS Demo

Demo for Service Registy, Client side load balancing (Spring Boot, Feign Client), internal routing (C2C networking), canary releases of new versions on Tanzu Application Services.

## Important cf commands

```cf push -f <manifestfile>```

```cf create-service p.service-registry standard <servicename>
cf share-service <servicename> -s <otherspace>```

```cf add-network-policy frontend-service --destination-app backend-service --protocol tcp --port 8080
cf add-network-policy <app> --destination-app <target> --protocol tcp --port 8080 -s <space>```
