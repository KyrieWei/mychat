package kyrie.mychat;

/**
 * Created by Kyrie_wei on 01/05/2017.
 */

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static final String IP_ADDR = "23.106.150.31";
    public static final int PORT = 4000;
    public  Socket socket;
    public  void run() {

        while (true) {
            try {
                System.out.println("we begin the outputstream!!!!!!!");
                socket = new Socket("23.106.150.31", PORT);
                System.out.println("we new the socket...........");
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                String str = "hello kyrie";

                out.writeUTF(str);
                System.out.println("we have written str!!!!!!!");
                String ret = input.readUTF();
                if ("OK".equals(ret)) {
                    Thread.sleep(500);
                    break;
                }

                out.close();
                input.close();
            } catch (Exception e) {
                return;
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        socket = null;
                        return;
                    }
                }
            }
        }
    }
}
