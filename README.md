# EPAM Java Lam module_04
## Business requirements
1. Develop web service for Gift Certificates system with the entities Certificate, Tag, Order, User
2. The system should expose REST APIs to perform the following operations:
    - CRUD operations for GiftCertificate. If new tags are passed during creation/modification – they should be created in the DB. For update operation - update only fields, that pass in request, others should not be updated. Batch insert is out of scope.
    - CRD operations for Tag.
    - Get certificates with tags (all params are optional and can be used in conjunction):
          * by tag name (ONE tag)
          * search by part of name/description (can be implemented, using DB function call)
          * sort by date or by name ASC/DESC (extra task: implement ability to apply both sort type at the same time).
3. Convenient error/exception handling mechanism should be implemented: all errors should be meaningful and localized on backend side. Example: handle 404 error:
 * HTTP Status: 404
 * response body    
 * {
 * “errorMessage”: “Requested resource not found (id = 55)”,
 * “errorCode”: 40401
 * }
where *errorCode* is your custom code (it can be based on http status and requested resource - certificate or tag)
4. Change single field of gift certificate (e.g. implement the possibility to change only duration of a certificate or only price).
5. Implement only get operations for user entity.
6. Make an order on gift certificate for a user (user should have an ability to buy a certificate).
7. Get information about user’s order: cost and timestamp of a purchase.The order cost should not be changed if the price of the gift certificate is changed.
8. Get the most widely used tag of a user with the highest cost of all orders.
9. Demonstrate SQL execution plan for this query (explain).
10. Search for gift certificates by several tags (“and” condition).
11. Pagination should be implemented for all GET endpoints. Please, create a flexible and non-erroneous solution. Handle all exceptional cases.
12. Support HATEOAS on REST endpoints.
13. Hibernate should be used as a JPA implementation for data access.
14. Spring Transaction should be used in all necessary areas of the application.
15. Audit data should be populated using JPA features.
16. Spring Security should be used as a security framework.
17. Application should support only stateless user authentication and verify integrity of JWT token.
18. Users should be stored in a database with some basic information and a password.
19. User Permissions:

 - Guest:
    * Read operations for main entity.
    * Signup.
    * Login.
 - User:
    * Make an order on main entity.
    * All read operations.
 - Administrator (can be added only via database call):
    * All operations, including addition and modification of entities.

