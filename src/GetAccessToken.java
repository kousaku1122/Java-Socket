import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;

public class GetAccessToken {
    public static void main(String[] args) throws IOException, InterruptedException {
        String data = "grant_type=password&client_id=43c626d2-db5c-4c3a-84e4-df843cddb516&client_secret=xh2qR.6xM-2G3Ar.hbqNKoaA@-Ow_nw:&username=test01@ejantest.onmicrosoft.com&password=test11!!&resource=https://graph.microsoft.com&scope=openid";

        SSLSocketFactory factory =
                (SSLSocketFactory)SSLSocketFactory.getDefault();
        SSLSocket socket =
                (SSLSocket)factory.createSocket("login.microsoftonline.com", 443); //SSLソケット

        String path = "/51aee0f2-088d-4d62-aa6b-e8bc57757c49/oauth2/token";

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write("POST " + path + " HTTP/1.1\r\n");//version
        writer.write("Host: login.microsoftonline.com" + "\r\n");
        writer.write("Accept: */*" + "\r\n");
        writer.write("Content-Type: application/x-www-form-urlencoded\r\n");
        writer.write("Content-Length: " + data.length() + "\r\n");
        writer.write("Connection: close" + "\r\n"); //コネクションを切るかどうか?

        System.out.print("POST " + path + " HTTP/1.1\r\n");
        System.out.print("Host: login.microsoftonline.com" + "\r\n");
        System.out.print("Accept: */*" + "\r\n");
        System.out.print("Content-Type: application/x-www-form-urlencoded\r\n");
        System.out.print("Content-Length: " + data.length() + "\r\n");

        writer.write("\r\n");

        writer.write(data);
        writer.flush();

        String responseLine;
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        while ((responseLine = reader.readLine()) != null) {
            System.out.println(responseLine);
        }

        writer.close();
        reader.close();
        socket.close();
    }
}
