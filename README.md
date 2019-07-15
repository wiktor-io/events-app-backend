# Events App Backend Server

This is a GraphQL server powered by Spring Boot. It will serve data to the Angular frontend UI.

## Usage

This project requires Docker to work. Alternatively one can set up MySQL and compile the backend and frontend and run them individually.

1. Clone this repository (this is the only repository you will need to run both frontend and backend of the app. I have added a "docker-compose.yml" file which includes the image for the frontend app as well).
2. While in the project directory run `docker-compose up -d`. Make sure your machine can listen on the following ports: 4200, 8080.
3. Note: java doesn't wait for the MySQL connection to be ready so the backend may crash if mysql doesn't initialise on time. Wait a few minutes before running `docker-compose up -d` again to fix this*.
4. Visit http://localhost:4200/ to preview the app.

This is a common issue for docker containers and is usually solved by using a wait-for-it script. In this example I am using a jib image which does not offer this option.

## TODOs

This was an exciting project where I have challanged myself to learn as much as possible in a week.
I have never used Spring or JPA before, and Angular has changed significantly across the years.
Additionally i had the opportunity to explore GraphQL for the first time and get an in-depth look into TDD with Angular. I believe I have learned
a great deal from this experience and I will be able to apply those skills in future.

With that said, I have made compromises in delivering this app. In this limited time, my focus was on acquiring new skills
 and demonstrating ability to adapt to new technologies.
 
Some of the technical tradeoffs that are noticable in this application:

- Monolithic components (frontend)
- Clunky GraphQL implementation (graphql-java)
- Client side pagination
- No Java unit testing
- No 3rd party component unit testing (frontend)
- Form / Data validation
- Data formatting

## Basic requirements / assumptions / opinions

1. This application is targeted at the event organiser admin user.
1. Backend server provides a CRUD for managing events.
1. Users must be able to filter events. The backend should be responsible for the filtering.
1. This application **could** be as simple as a few lines of code. However, I chose to use this opportunity to experiment with GraphQL. By utilising GraphQL queries I should be able to make the Angular app more streamlined and make less HTTP requests to fetch data.
1. I chose to write the server in Java for exercise.

## Entity

Simplified data design with only two entities. This is to test GraphQL's features.  

| Event
|---|
| pk: id
| fk: organiser
| name
| description
| venue
| venue_location
| availability
| capacity
| type
| category
| status
| recurrence
| date
| image
| price

| Organiser
|---|
| pk:id
| name
| logo
| description

## Docker Jib

``` bash
./gradlew jib --image wkarpinski/events-app-backend
``` 
