Atm System using JWT

LoginService is the main front controller which communicated with the Authentication server to get the JWT token.
Later client uses JWT token to communicate with bank server for user data manipulation.

I have RSA512 algo rythm to sign the user claims. it is Asymetric key algo which ises different private key and publickey to validate JWT token on request arrival
