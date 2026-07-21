# FaceRecon

FaceRecon is a small Spring Boot-based Java service for image upload and face recognition. It provides a file-watcher for incoming images, REST endpoints to manage images, and integration points for representation/verification analysis inside the `imageRecognition` module.

## Features

- File watching and ingestion of images (see `config` and `fileWatcher` packages)
- REST endpoints for uploading and retrieving image records (`controller` package)
- Image processing and face recognition logic under `imageRecognition` (represent/verify/analyze)
- Simple persistence via the `repository` layer; `uploads/` is used for file storage
- Docker and Docker Compose support

## Project layout (high level)

- `src/main/java/com/faceRecon/config` — application configuration, file watcher, seeding
- `src/main/java/com/faceRecon/controller` — REST controllers (`FileController`, `ImageController`)
- `src/main/java/com/faceRecon/service` — business logic (`FileService`, `ImageService`)
- `src/main/java/com/faceRecon/imageRecognition` — recognition client and DTOs
- `src/main/java/com/faceRecon/model` and `repository` — domain models and persistence
- `uploads/` — runtime folder for uploaded image files

## Requirements

- Java 11+ (or a modern JDK compatible with your Spring Boot version)
- Maven (the repository includes the Maven wrapper `mvnw`)
- Docker (optional, for container runs)

## Build & Run

Using the included Maven wrapper:

```bash
./mvnw clean package
./mvnw spring-boot:run
```

Or build and run the jar:

```bash
./mvnw package
java -jar target/*.jar
```

The application defaults to port 8080 (check `application.properties`).

## Docker

Build the image and run:

```bash
docker build -t facerecon .
docker run -p 8080:8080 -v "$PWD/uploads":/app/uploads facerecon
```

Or use Docker Compose:

```bash
docker-compose up --build
```

## Configuration

- `src/main/resources/application.properties` and `application-dev.properties` contain configuration values and profiles. Adjust properties for storage paths, ports, and any external API keys used by the recognition client.

## API / Usage

Check the controllers for exact endpoint paths and request formats. Typical usage patterns include:

- Upload an image (multipart/form-data):

```bash
curl -X POST -F "file=@/path/to/photo.jpg" http://localhost:8080/api/images
```

- List or fetch images (replace with actual endpoints found in `ImageController`):

```bash
curl http://localhost:8080/api/images
```

Adjust endpoint paths as implemented in `FileController` and `ImageController`.

## Development notes

- `DataSeeder` seeds initial data on startup (see `config/DataSeeder.java`).
- `CustomerFileWatcherConfig` and `ImageFileListener` handle watching a configured directory for new files.
- The `imageRecognition` package contains the client and DTOs used to call or mock face recognition services.

## Where to look next

- Implementation of endpoints: `src/main/java/com/faceRecon/controller` ([FileController](src/main/java/com/faceRecon/controller/FileController.java), [ImageController](src/main/java/com/faceRecon/controller/ImageController.java))
- Image processing flow: `src/main/java/com/faceRecon/imageRecognition`
- Storage and uploads: `uploads/` and `src/main/resources/application*.properties`
