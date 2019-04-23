import java.util.StringTokenizer;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.io.File;

public class Request {
    private static File pagesPath = new File("./.static");

    private String method;
    private String resource;

    public Request(String headerLine) {        
        StringTokenizer parse = new StringTokenizer(headerLine);
        this.method = parse.nextToken().toUpperCase();
        this.resource = parse.nextToken().toLowerCase();
    }

    public String getResource() {
        return resource;
    }

    public String getMethod() {
        return method;
    }
    
    public ResponseBuilder createResponse() {
        if(resource.equals("/")) {
            resource = "index.html";
        }

        File file = new File(pagesPath, resource);
        if(!file.exists()) {
            return new ResponseBuilder().withStatusCode(StatusCode.NotFound)
                                        .addResponseFile(new File(pagesPath, "default/not_found.html"));
        }
        
        return new ResponseBuilder().withStatusCode(StatusCode.Ok).addResponseFile(file);
    }
}