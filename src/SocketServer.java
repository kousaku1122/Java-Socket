import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String[] args) {
        //HTTP通信ポート番号80、HTTP通信ポート番号443、ポート8080番は代替HTTPポート
        try (ServerSocket server = new ServerSocket(8080)){

            while(true) {
                //接続待ち
                Socket socket = server.accept();
                System.out.println("クライアントからの接続がありました。");

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line = reader.readLine();
                    StringBuilder header = new StringBuilder(); //header
                    String body = null; //body
                    int contentLength = 0;

                    while (line != null && !line.isEmpty()) {
                        if (line.startsWith("Content-Length")) {
                            contentLength = Integer.parseInt(line.split(":")[1].trim());
                        }
                        header.append(line + "\n");
                        line = reader.readLine();
                    }

                    if (0 < contentLength) {
                        char[] c = new char[contentLength];
                        reader.read(c);
                        body = new String(c);
                    }

                    System.out.println(header);
                    System.out.println(body);

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    writer.write("HTTP/1.1 200 OK\r\n");
                    writer.write("Content-Length: " + ("The username is "+body).length() + "\r\n");
                    writer.write("Connection: close\r\n"); //コネクションを切るかどうか?
                    writer.write("\r\n");

                    writer.write("The username is " + body);
                    writer.flush();
                    reader.close();
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}