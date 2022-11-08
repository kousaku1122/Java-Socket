import java.io.*;
import java.net.*;
public class SendRequest {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8080; //ポート番号
        // クライアントソケットを生成
        try (Socket socket = new Socket(host,port);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             // キーボード入力用のリーダーの作成
             BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))) {
            // 「exit」を入力するまで繰り返し
            while (true) {
                System.out.print("IN>");
                String input = keyboard.readLine();
                writer.println(input);
                if (input.equals("exit")) {
                    break;
                }
                System.out.println("[サーバーからの応答]" + reader.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}