import java.util.Arrays;
import java.util.Map;

public class StatusCode {
    public static final StatusCode Ok = new StatusCode(200, "OK");
    public static final StatusCode NotFound = new StatusCode(404, "Not Found");
    public static final StatusCode InternalServerError = new StatusCode(500, "Internal Server Error");

    private static StatusCode[] todos = new StatusCode[]
    {
        Ok, NotFound, InternalServerError
    };

    private int code;
    public String description;

    private StatusCode(int code, String descr) {
        this.code = code;
        this.description = descr;
    }

    public int getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public static final StatusCode fromIntStatus(final int code)
    {
        return Arrays.stream(todos).filter(x -> x.getCode() == code).findFirst().get();
    }
}