FROM gcr.io/distroless/java21-debian12:nonroot
COPY build/libs/digihot-oppslag-all.jar /app.jar
ENV TZ="Europe/Oslo"
EXPOSE 8080
CMD ["/app.jar"]
