public class Request {
    private String method;
    private String resource;

    public Request(String method, String resource) {
        this.method = method;
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }

    public String getMethod() {
        return method;
    }
}