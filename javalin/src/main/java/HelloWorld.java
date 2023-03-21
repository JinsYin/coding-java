import io.javalin.Javalin;

public class HelloWorld {
    public static void main(String[] args) {
        var app = Javalin.create(/*config*/)
                .get("/", (io.javalin.http.Context ctx) -> ctx.result("Hello World"));
        app.start(7070);
    }
}