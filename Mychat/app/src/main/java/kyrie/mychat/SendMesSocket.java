package kyrie.mychat;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.SocketHandler;

/**
 * Created by kyrie on 2017/5/6.
 */

public class SendMesSocket {
    private boolean isStartRecieveMsg;
    private Socket mSocket;

    private SocketHandler mHandler;
    protected BufferedReader mReader;
    protected BufferedWriter mWriter;
    public final static String IP_ADDR = "23.106.150.31";
    public final static int PORT = 5000;

    public SendMesInfo sendMesInfo = new SendMesInfo();

    private void initData(){
        //sendMesInfo.initData();
        mHandler = new SocketHandler();
    }

    private void initSocket(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    isStartRecieveMsg = true;
                    mSocket = new Socket(IP_ADDR,PORT);
                    mReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream(),"utf-8"));
                    mWriter = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream(),"utf-8"));
                    while(isStartRecieveMsg){
                        if(mReader.ready()){
                            String data = mReader.readLine();
                            mHandler.obtainMessage(0,data).sendToTarget();
                        }
                        Thread.sleep(200);
                    }
                    mWriter.close();
                    mReader.close();;
                    mSocket.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void send() {
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                sendMsg();
                return null;
            }
        }.execute();
    }

    protected void sendMsg() {
        try {
            JSONObject json = new JSONObject();
            json.put("to", sendMesInfo.username_to);
            json.put("msg", sendMesInfo.sendMes);
            json.put("from", sendMesInfo.username_from);
            mWriter.write(json.toString()+"\n");
            mWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class SocketHandler extends Handler {

        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    try{
                        JSONObject json = new JSONObject((String)msg.obj);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
