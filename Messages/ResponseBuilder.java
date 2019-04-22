import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class ResponseBuilder {
    private Response response;

    public ResponseBuilder() {
        this.response = new Response();
    }

    public ResponseBuilder withHeader(String key, String value) {
        return this;
    }

    public ResponseBuilder withStatusCode(int statusCode) {
        this.response.setStatusCode(statusCode);
        return this;
    }

    public ResponseBuilder withStatusCode(StatusCode statusCode) {
        this.response.setStatusCode(statusCode);
        return this;
    }

    public ResponseBuilder configureOutputStream(OutputStream outputStream) {
        this.response.setOutputStream(outputStream);
        return this;
    }

    public ResponseBuilder addHeader(String key, String value) {
        this.response.addHeader(key, value);
        return this;
    }

    public ResponseBuilder addResponseFile(File file) {
        int fileLength = (int)file.length();

        try{
            this.response.addResponseData(Files.readAllBytes(file.toPath()), fileLength);
        }catch(IOException ioex) {
            // TODO:
        }
                
        this.response.addHeader("Content-Length", String.valueOf(fileLength));
        return this;
    }

    public Response build() {
        return response;
    }
}