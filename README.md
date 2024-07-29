# GRInvAcc

This project is a self-hosted Profit Accounting and Inventory program, using a Kotlin backend via Ktor and a basic web-based frontend.

## Features
- Main dashboard with all profit/loss and inventory information available.
- Easy entry of sales & buying data, along with adding items to the system.
- Allows you to see all sales/buys and manually edit and delete them.

## Setup

These instructions assume you are familiar with PostgreSQL, and general project setup.

1. Create a PostgreSQL Database, for said database create a user with `SUPERUSER` permissions
3. Replace the placeholder data in the `sqlConfig.properties` file located in the root of the project with your database URL, username & password
4. Run the project and open up `localhost:6450` for the frontend interface