import java.io.*;
import java.net.*;
public class SocketServer {
    public static void main(String[] args) {
        //HTTP通信ポート番号80、HTTP通信ポート番号443
        try (ServerSocket server = new ServerSocket()) {
            while (true) {
                server.bind(new InetSocketAddress("localhost", 8080));
                try {
                    // -----------------------------------------
                    // 2.クライアントからの接続を待ち受け（accept）
                    // -----------------------------------------
                    Socket sc = server.accept();
                    System.out.println("クライアントからの接続がありました。");
                    BufferedReader reader = null;
                    PrintWriter writer = null;
                    // -----------------------------------------
                    // 3.クライアントからの接続ごとにスレッドで通信処理を実行
                    // -----------------------------------------
                    try {
                        // クライアントからの受取用
                        reader = new BufferedReader(new InputStreamReader(sc.getInputStream()));
                        // サーバーからクライアントへの送信用
                        writer = new PrintWriter(sc.getOutputStream(), true);
                        // クライアントから「exit」が入力されるまで無限ループ
                        String line = null;
                        while (true) {
                            // クライアントから送信されたメッセージを取得
                            line = reader.readLine();
                            if (line.equals("exit")) {
                                break;
                            }
                            System.out.println("クライアントからのメッセージ＝" + line);
                            writer.println("Please input:");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        // リソースの解放
                        if (reader != null)
                            reader.close();
                        if (writer != null)
                            writer.close();
                        if (sc != null)
                            sc.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}