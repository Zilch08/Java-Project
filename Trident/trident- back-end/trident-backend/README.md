# Trident Java Backend

Requirements: JDK 17+, Maven 3.9+, MySQL 8+.

1. Set MySQL credentials in `src/main/resources/application.properties` or environment variables `DB_USERNAME`, `DB_PASSWORD`, `DB_URL`.
2. Set `JWT_SECRET` to a strong random secret (at least 32 bytes) before use beyond local development.
3. Run `mvn spring-boot:run`. API base: `http://localhost:8080`.
4. Tables are created/updated by Hibernate for local development.

## Auth
`POST /api/auth/register`
`{"fullName":"Asha Rao","email":"asha@example.com","password":"StrongPass123","role":"CANDIDATE"}`

Roles: `CANDIDATE` or `RECRUITER`. Login: `POST /api/auth/login` with email/password. Use returned token in `Authorization: Bearer <token>`.

## Endpoints
- `GET /api/jobs?q=java&location=Delhi&type=Internship&workMode=Remote`
- `GET /api/jobs/{id}`
- `POST /api/jobs` recruiter-only
- `PUT /api/jobs/{id}` owner recruiter/admin
- `DELETE /api/jobs/{id}` closes job
- `GET /api/jobs/mine`
- `GET /api/profile/me`, `PUT /api/profile/me`
- `POST /api/applications` body `{"jobId":1,"coverLetter":"Why I am applying"}`
- `GET /api/applications/mine`
- `GET /api/applications/received`
- `PATCH /api/applications/{id}/status` body `{"status":"SHORTLISTED"}`

Application statuses: SUBMITTED, REVIEWING, SHORTLISTED, REJECTED, HIRED.

## Frontend
Call `http://localhost:8080/api/...` from Live Server. Add `Authorization: Bearer ${token}` to protected requests. This is a starter implementation; before public deployment add DTOs that never serialize password fields, migrations, rate limits, email verification, password reset, pagination, audit logging and hardened secrets.
