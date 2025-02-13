Atm System using JWT

The LoginService serves as the primary front controller, facilitating communication with the authentication server to obtain a JWT (JSON Web Token). 
Subsequently, the client utilizes the JWT token to interact with the bank server for user data manipulation. 
The system employs the RSA-512 algorithm to sign user claims. 
This is an asymmetric key algorithm that utilizes distinct private and public keys to sign and validate JWT tokens upon request arrival.

pom.xml contains all the dependencies used.
