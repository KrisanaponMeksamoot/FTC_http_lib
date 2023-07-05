import java.io.IOException;

import org.firstinspires.ftc.teamcode.http.Server;

public class Test {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start(80);
    }
}
