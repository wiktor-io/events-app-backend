# Events App Backend Server

This is a GraphQL server powered by Spring Boot. It will serve data to the Angular frontend UI.

## Basic requirements / assumptions / opinions

1. This application is targeted at the organiser.
1. Backend server provides a CRUD for managing events.
1. Users must be able to filter events. The backend should be responsible for the filtering.
1. This application **could** be as simple as a few lines of code. However, I chose to use this opportunity to experiment with GraphQL. By utilising GraphQL queries I should be able to make the Angular app more streamlined and make less HTTP requests to fetch data.
1. I chose to write the server in Java for excercise.

## Entity

Simplified data design with only two entities. This is to test GraphQL's features.  

| Event
|---|
| pk: id
| fk: organiser
| name
| descrition
| venue
| venue_location
| availability
| date
| image
| price

| Organiser
|---|
| pk:id
| name
| organiser_logo
| description
