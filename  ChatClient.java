import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 1234);

        BufferedReader input = new BufferedReader(
            new InputStreamReader(System.in)
        );
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
            new InputStreamReader(socket.getInputStream())
        );

        // read messages from server
        new Thread(() -> {
            try {
                String msg;
                while ((msg = in.readLine()) != null) {
                    System.out.println(msg);
                }
            } catch (Exception e) {}
        }).start();

        // send messages
        String userMsg;
        while ((userMsg = input.readLine()) != null) {
            out.println(userMsg);
        }
    }
}