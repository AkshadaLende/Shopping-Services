The project is still in-development.
API Gateway
│
└── discovery-server:8761

Product Service
│
├── discovery-server:8761
└── mongo:27017

Order Service
│
├── discovery-server:8761
└── mysql:3306/order-service

Inventory Service
│
├── discovery-server:8761
└── mysql:3306/inventory-service

Auth Service
│
├── discovery-server:8761
└── its database, depending on your setup

API Gateway
│
└── redis:6379