import java.io.IOException;

import net.kris.ftc.http.HttpRequest;
import net.kris.ftc.http.HttpResponse;
import net.kris.ftc.http.Server;
import net.kris.ftc.http.ws.WSFrame;
import net.kris.ftc.http.ws.WSUtils;
import net.kris.ftc.http.ws.WebsocketHandler;

public class Test {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start(80);
        server.setHandler((con) -> {
            try {
                con.readHeader();
                HttpRequest req = con.getRequest();
                HttpResponse res = con.getResponse();

                if (req.getURI().getPath().equals("/ws")) {
                    // System.out.println(req);
                    if (WebsocketHandler.isWebsocket(req)) {
                        WebsocketHandler ws = new WebsocketHandler(con);
                        ws.sendHeader();
                        ws.send(new WSFrame(true, WSUtils.OP_TEXT, "Hello, World!".getBytes()));
                        while (true) {
                            WSFrame packet = ws.receive();
                            System.out.printf("%d: %s", packet.opcode, new String(packet.data));
                            if (packet.opcode == WSUtils.OP_CLOSE) {
                                ws.send(new WSFrame(true, WSUtils.OP_CLOSE, new byte[] { 0x03, -24 }));
                                ws.close();
                            }
                        }
                    } else {
                        byte[] data = "<html><head></head><body><h1>Bad Request!</h1><p>Websocket!</p></body></html>"
                                .getBytes();
                        res.setStatus("400 BAD REQUEST");
                        res.putAttribute("Content-Type", "text/html");
                        res.putAttribute("Content-Length", Integer.toString(data.length));
                        con.sendHeader();
                        con.getOutputStream().write(data);
                        con.close();
                    }
                }

                byte[] data = "<html><head></head><body><h1>Server is started and working!</h1><p>Test Only!</p></body></html>"
                        .getBytes();
                res.setStatus("200 OK");
                res.putAttribute("Content-Type", "text/html");
                res.putAttribute("Content-Length", Integer.toString(data.length));
                con.sendHeader();
                con.getOutputStream().write(data);
                con.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
