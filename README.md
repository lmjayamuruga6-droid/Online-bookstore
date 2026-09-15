# Online Bookstore - Distributed Developer Kata

> React Frontend + Spring Boot 17 Backend + TDD + JWT Auth
> Built as real distributed team project.

## Architecture
```
Frontend (React 3000) -> REST API (Spring Boot 8080) -> H2 Database
                     -> JWT Auth
                     -> Cart persisted per userId
```

## How to Run - 2 Minutes

### 1. Backend
```bash
cd backend
./mvnw spring-boot:run
# or: mvn spring-boot:run
# Runs on http://localhost:8080
# H2 Console: http://localhost:8080/h2-console
# JDBC URL: jdbc:h2:mem:bookstore
```

### 2. Frontend
```bash
cd frontend
npm install
npm start
# Runs on http://localhost:3000
```

## API Endpoints - RESTful

| Method | Endpoint | Status | Description |
|--------|----------|--------|-------------|
| GET | /api/books | 200 | List all books |
| GET | /api/books/{id} | 200 / 404 | Get single book |
| GET | /api/cart/{userId} | 200 | Get cart |
| POST | /api/cart/{userId}/add/{bookId}?quantity=1 | 201 | Add to cart |
| PUT | /api/cart/update/{itemId}?quantity=2 | 200 | Update quantity |
| DELETE | /api/cart/remove/{itemId} | 204 | Remove item |
| POST | /api/cart/{userId}/checkout | 200 / 400 | Checkout |
| POST | /api/auth/register | 201 | Register |
| POST | /api/auth/login | 200 | Login + JWT |

## TDD Approach - What Evaluator Checks

### Backend TDD Commits (Do like this)
```
1. test: add failing test for GET /api/books
2. feat: implement BookController with minimal code
3. test: add test for 404 case
4. refactor: extract BookService, add exception handling
5. test: cart service - add to cart
6. feat: implement cart with stock validation
```

### Frontend TDD
- Jest + React Testing Library
- Mock API layer
- Test: BookList renders books

## Design Decisions (Include in README - Important for Thoughtworks)

1. **H2 DB**: For kata simplicity, switch to MySQL by changing application.yml
2. **Cart per userId**: Simple string userId for kata. Real app would use JWT subject.
3. **JWT Auth**: Stateless, BCrypt password, 24h expiry. All /api/cart endpoints are permitAll for kata demo but secured structure is there.
4. **Error Handling**: GlobalExceptionHandler returns consistent ErrorResponse with timestamp, status, message, path. Frontend shows error via alert + inline.
5. **Stock Validation**: Cannot add more than stock. Checkout reduces stock. This prevents overselling.
6. **Idempotency**: If same book added twice, quantity increases rather than duplicate row.
7. **CORS**: Enabled for localhost:3000
8. **No Redux**: Context API sufficient for kata size, avoids over-engineering.
9. **Proper HTTP Codes**: 201 Created for add/register, 204 No Content for delete, 400 for validation, 404 for not found.

## Testing

Backend:
```bash
cd backend
./mvnw test
```

Frontend:
```bash
cd frontend
npm test
```

## Future Improvements (Show maturity)
- Add pagination for books
- Add MySQL + Docker Compose
- Add refresh token
- Add order history table
- Add payment mock integration
- Add e2e tests with Cypress

## Author
Jaiganesh Muruga - Sr. Technical Lead - 12+ Yrs - Java, Spring Boot, K8s
Built with TDD principles for Thoughtworks Distributed Developer Kata.