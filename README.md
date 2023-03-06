# Spring, MongoDB, Reactive

This is implementation of Shopping Cart service.

## Several Key points:
- Session management does not a part of this service! 
- This service required JWT token in header (for the anonymous users also)
- For simplicity(for the example) encryption and decryption of JWT token does not provided.
- Test JWT token required one field "sid" (OpenId specification) which is using as Shopping Cart ID
- Local ("dev" profile) use the embedded MongoDb which is starting with application
- Check style add as example (checkstyle.xml - does not contain correct rules)
- Only one profile "dev" implemented (other "prod" and "cloud" are not) 
- Deployment scripts/code does not implemented 

## Not include
- JWT encryption and decryption
- Swagger API
- Deployment scripts
- Correct checkstyle 

## Run as a native application
We can compile this app as a native application
```
./buildGraalVm.sh
```

## Run application locally
```
./startLocal.sh
```

## Postman collection for API tests
```
test.postman_collection.json
```