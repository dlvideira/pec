# PEC - Personal Expenses Control

PEC is a multi language study project (Java backend / React frontend), with MongoDB as database.

It is a financial app that allows user to manage expenses.
It also has a bank account manager built in.

The project is not fixed and could implement new frameworks for test/learning purposes.

## Dependencies

- Java 13 or above
- Maven
- MongoDB (You can create a free count to use Atlas (https://www.mongodb.com/cloud/atlas) to host it online or use it local)

## Usage

To run backend, after everything above is set, and the repository is cloned locally, run in /pec directory:

```bash
mvn spring-boot:run
```
Now that you have the backend running (localhost:8080), for the frontend, in another terminal run:
```bash
"/pec/src/main/frontend"
npm install
npm start
```
Now you have the frontend running on localhost:3000.

## Notes

- For development purposes, sercurity is implemented, but disabled;
- You can create free tier account in MongoDB Atlas or use locally. You just need to configure the uri on application properties;
- When you launch the application and your DB is clean, it will create a new user with the credentials defined in application-default.yml;
- CORS in backend is configured to allow ALL requests, remember to change/limit before using in production.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.