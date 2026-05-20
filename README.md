# Mealpoints Exchange App 
An Android peer-to-peer marketplace application that allows university students to securely buy, sell, and track campus meal-point balances.

---
### Tech Stack & Architecture
This application is architected using **MVVM (Model-View-ViewModel)** design patterns to keep a clean separation of concerns:

* **Language:** Kotlin
* **UI Framework:** Jetpack Compose 
* **Local Database:** Room SQLite DB (with Data Access Objects)
* **State Management:** ViewModels & Kotlin asynchronous Flows
* **Testing Suite:** JUnit & Android Instrumented Tests
  
---
### Key Features
* **Peer-to-Peer Marketplace:** Users can seamlessly browse, post, and accept meal-point listings available across campus.
* **Dynamic Content Filtering:** A functional filtering system that allows users to instantly sort and screen available  listings based on custom criteria (such as price).
* **Persistent User Profiles:** Saves and updates user details, available point balances, and active roles.
* **Reactive UI States:** Real-time data sync between database layers and the user interface using asynchronous Coroutines and Flows.

---
### Database Schema Overview
The local persistence engine relies on a relational Room Database containing three core tables:
1. **User Profile Table:** Manages distinct student user accounts and balances.
2. **Listings Table:** Stores current market listings, meal-point amounts, and asking prices.
3. **Transaction History Table:** Logs complete transaction details for auditable data persistence.

---
### Contributors
* **Diya Patel** (@Diya-Patelll)
* **Connor Nylund** (@ConnorNylund)
