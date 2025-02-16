# Library Management System

## Technology Stack
- **Java Version:** 17
- **Spring Boot Version:** 3.3.8
- **Authentication & Authorization:** JWT (JSON Web Token)
- **Validation:** Proper request validation is implemented
- **Shared Response:** Standardized response structure for API responses

## Features
- Secure authentication and authorization using JWT
- Role-based access control
- Proper validation for API requests
- Shared response structure for consistent API responses
- Pre-initialized users for testing

## Available Endpoints
The repository includes a **Postman collection** that lists all available endpoints for easy API testing. Import the collection into Postman to explore the API.

## Pre-Initialized Users
Two users are initialized in the database:

1. **Admin User**  
   - **Username:** `admin`  
   - **Password:** `admin`  
   - **Role:** Admin with access to all endpoints

2. **Librarian User**  
   - **Username:** `librarian`  
   - **Password:** `librarian`  
   - **Role:** Librarian with restricted access (cannot manage user endpoints)

## Tests

The **Borrow Book** endpoint is covered by automated tests to ensure its reliability and correctness. These tests validate different scenarios, including:

- Successful book borrowing
- Borrowing a book that is already borrowed
- Return Borrwed Book
- Return not Borrwed Book

## How to Use
1. Clone the repository
2. Configure the database
3. Run the application
4. Use Postman to test the available endpoints using the provided credentials


