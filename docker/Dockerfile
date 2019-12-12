FROM docker.artifactory.neovest.com/idp-base

COPY simple-rest-demo.jar simple-rest-demo.jar

EXPOSE 8889

CMD ["java","-Dserver.port=8889","-jar","simple-rest-demo.jar"]
