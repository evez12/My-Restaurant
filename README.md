# My Restaurant Web Application

## Overview
My Restaurant is a web application that allows users to perform the following actions:

1. Register an account to access the platform.
2. Place orders for products available in the restaurant.
3. Reserve tables at the restaurant conveniently through the application.

---

## Problem Description
Managing reservations and product orders can be challenging for restaurants and their customers. Traditional methods, such as phone calls or in-person reservations, are often time-consuming and prone to errors. Customers may face difficulty finding available tables or ordering products during peak hours. Additionally, restaurants may struggle to keep track of reservations and orders efficiently.

---

## Approach to Solve the Problem(package/file structure)
To address the identified issues, this application organizes its codebase using a modular structure, focusing on scalability and maintainability. Two common approaches for structuring the codebase are:

### a) Package by Feature
- **Definition:** Group related components (e.g., controllers, services, repositories) based on specific features or modules like `Order`, `Reservation`, and `User`.
- **Advantages:** Easier to maintain and scale individual features. Promotes modularity and allows independent development and testing of each feature.

### b) Package by Layer
- **Definition:** Group similar components (e.g., all controllers, all services, all repositories) into separate packages like `controllers`, `services`, and `repositories`.
- **Advantages:** Provides a clear separation of concerns. Useful for small-scale applications with less complexity.

---

## Selected Approach: Package by Feature
The `Package by Feature` approach is chosen for the following reasons:

### a) Future Transition to Microservices
Organizing the codebase by feature simplifies the transition to a microservices architecture. Each feature is self-contained, allowing it to be extracted into an independent service with minimal changes to other parts of the application. This modular structure reduces interdependencies, making the application easier to scale and adapt to future requirements.

### b) High Cohesion and Low Coupling
- **High cohesion and low coupling are essential principles in software design that enhance modularity, maintainability, and scalability.**
- **High Cohesion:** High Cohesion ensures that elements within a module are closely related and work together to achieve a single purpose. In the Package by Feature approach, all classes and components related to a specific feature, such as Order, Reservation, or User, are grouped together. This improves understandability and reusability, as the functionality within each package is focused and directly related.

- **Low Coupling:** Low Coupling minimizes dependencies between different feature packages. Each package is designed to operate independently, reducing the ripple effects of changes. This makes the system more robust and easier to maintain, as updates to one package are less likely to impact others. Additionally, this structure supports scalability and aligns well with a microservices architecture, where each service can function as an independent unit.
---

## How to Use

### Prerequisites
- Ensure you have Java and Maven installed.
- Set up a compatible database (I used PostgreSQL) and configure the application properties accordingly.
- Use a modern browser for the best user experience.

### Steps to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/evez12/My-Restaurant.git
   cd My-Restaurant
   ```
2. Build the application using Maven:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. Access the web application at:
   ```
   http://localhost:8080
   ```

### Key Features
- **User Registration:** Sign up to create an account and log in to access features.
- **Product Orders:** Browse products and place orders online.
- **Table Reservations:** Select and book tables for your preferred date and time.

---

## Contributing
1. Fork the repository.
2. Create a new branch:
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add your feature description"
   ```
4. Push to the branch:
   ```bash
   git push origin feature/your-feature-name
   ```
5. Create a pull request.

---

## License
This project is licensed under the [MIT License](LICENSE).

---

For more details, visit the repository: [My Restaurant GitHub Repo](https://github.com/evez12/My-Restaurant).

