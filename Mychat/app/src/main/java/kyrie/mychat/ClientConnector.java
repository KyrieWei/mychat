package kyrie.mychat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;


/**
 * Created by Kyrie_wei on 01/05/2017.
 */

public class ClientConnector {

    private Socket Client;
    private String DstIP;
    private int DstPort;
    private ConnectListener Listener;

    public ClientConnector(String dstIP, int desPort){
        this.DstIP = dstIP;
        this.DstPort = desPort;
    }

    //connect to server
    public void connect() throws IOException{
        Client = new Socket(DstIP,DstPort);
        DataOutputStream outputStream = new DataOutputStream(Client.getOutputStream());
        String str = "hello kyrie!";
        outputStream.writeUTF(str);
        outputStream.close();
    }

    public void send(String receiver, String data) throws IOException{
        OutputStream outputStream = Client.getOutputStream();
        outputStream.write((receiver + "#" + data).getBytes());
    }

    public  void setOnConnectListener(ConnectListener listener){
        this.Listener = listener;
    }

    public  interface ConnectListener{
        void onReceiveData(String data);
    }
}
