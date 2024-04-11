# my-dropbox-clone
Implemented a simplified Dropbox like Product, where users can upload and download files through a Web Application.

## Prerequisites
List everything needed to run your project:
- Docker
- Docker Compose
- Node.js and npm
- Java JDK (version 11 or higher)
- MySQL Workbench (optional, for database management)
- Any other necessary tools or dependencies

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Clone the Repository
Start by cloning the repository to your local machine:
```bash
git clone https://github.com/lksh97/DropLite.git
cd DropLite
```

### Frontend Setup
Navigate to the frontend directory, install dependencies, and start the React app:
```bash
cd ../frontend
npm install
npm start
```
This will start the React development server, typically on http://localhost:3000.

#### Run with Docker
To start the backend with Docker, make sure Docker is running and execute:
```bash
docker-compose up -d
```
This will start the MySQL database as defined in your `docker-compose.yml` file.

### Backend Setup
Navigate to the backend directory and build the project:
```bash
cd backend
./mvnw clean install
```

### Access the Application
- **Frontend**: Open a web browser and go to http://localhost:3000 to view the frontend.
- **Backend API**: Access the backend APIs via http://localhost:8080. Use tools like Postman or Swagger (if configured) to interact with the API.
- **Database**: Connect to the MySQL database using a GUI tool like MySQL Workbench. Connect to `localhost` on port `3306`.

## Built With
List the major frameworks or technologies used:
- [Spring Boot](https://spring.io/projects/spring-boot) - The backend framework
- [React](https://reactjs.org/) - The frontend framework
- [MySQL](https://www.mysql.com/) - Database
- [Docker](https://www.docker.com/) - Containerization