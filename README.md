# chatApplication
* This project is giving the information about chats of users.
## Framwork and Language use in this Project
* Springboot 
* java
## Dependencies
* Spring Web
* SQL Database
* hibernate
* lombok
* validation
* Spring devTools
* jpa
## validation
* validation apply on users class.
## Flow of project
* Create three layers model , service , controller, repository and util.
* In model layer create three classes first one is user class and status class and chatHistory class.
* In util class validate the user class attributes.
* In controller layer to performe CRUD operatiom by http request.
* In service layer write a business logic of CRUD operation.
* In repository layer exteds jpsRepositor.
## Constraints:
* doctor model will have-
* userId
* firstName,LastName,phoneNumber,email,age,gender...
* In user class connected with statusid by manyToOne relation.
* patient model will have:-
* patientid
* gender
* patientDisease
* patientName
* patientNuber
## Endpoints to provided :
* Adddoctor and patient
* update doctor by doctorId
* delete doctor by doctorId
* Get all doctor
* Get doctor by id
