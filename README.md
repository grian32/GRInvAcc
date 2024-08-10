# GRInvAcc

This project is a self-hosted Profit Accounting and Inventory program, using a Kotlin backend via Ktor and a basic web-based frontend.

## Main Features
- Main dashboard with all profit/loss and inventory information available.
- Easy entry of sales & buying data, along with adding items & extra expenses/profits to the system.
- Inventory dashboard that features all items and individual profits/inventory numbers for each item.

## Setup

These instructions assume you are familiar with PostgreSQL, and general project setup.

1. Create a PostgreSQL Database, for said database create a user with `SUPERUSER` permissions
2. Replace the placeholder data in the `sqlConfig.properties` file located in the root of the project with your database URL, username & password
3. Run the project via `./gradlew run` and open up `localhost:6450` for the frontend interface