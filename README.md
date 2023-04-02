# https://auth0.com/blog/spring-boot-java-tutorial-build-a-crud-api/
# https://auth0.com/blog/spring-boot-authorization-tutorial-secure-an-api-java/

#Run Docker image
docker run --env AUTH0_AUDIENCE --env AUTH0_DOMAIN -p 8088:8088 veeteq/auth0-backend
