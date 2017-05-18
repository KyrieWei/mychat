package kyrie.mychat;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.SocketHandler;

/**
 * Created by kyrie on 2017/5/6.
 */

public class SendMesSocket {

    private static final SendMesSocket INSTANCE = new SendMesSocket();
    private boolean isStartRecieveMsg;

    public BufferedReader mReader;
    public BufferedWriter mWriter;
    public final static String IP_ADDR = "23.106.150.31";
    public final static int PORT = 5000;

    public static Socket mSocket;
    public boolean onlineFriend;
    public ArrayList<SendMesInfo> msgArr = new ArrayList<SendMesInfo>();

    public SendMesInfo sendMesInfo = new SendMesInfo();
    public ArrayList<String> online_frilist = new ArrayList<String>();

    public static SendMesSocket getInstance(){
        return INSTANCE;
    }

//    public static void setSocket(Socket socketpass){
//        SendMesSocket.mSocket = socketpass;
//    }
//
//    public static Socket getSocket(){
//        return SendMesSocket.mSocket;
//    }
/*    public void connect() {
        try {
            mSocket = new Socket(IP_ADDR, PORT);
            mReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream(), "utf-8"));
            mWriter = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream(), "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public void initData(SendMesInfo sendMesInfo) {
        this.sendMesInfo = sendMesInfo;
    }


   /* public void startReceiveMsg(final SendMesInfo msg) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    isStartRecieveMsg = true;
                    mSocket = new Socket(IP_ADDR, PORT);
                    mReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream(), "utf-8"));
                    mWriter = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream(), "utf-8"));

                    JSONObject json_out = new JSONObject();
                    json_out.put("to", msg.username_to);
                    json_out.put("msg", msg.sendMes);
                    json_out.put("from", msg.username_from);
                    json_out.put("socketType", msg.socketType);

                    System.out.println("the info which is gonna be sent to server: " + json_out);

                    mWriter.write(json_out.toString() + "\n");
                    //System.out.println("the info which is gonna be sent to server: " + mWriter);
                    mWriter.flush();
                    while (isStartRecieveMsg) {
                        System.out.println("we are in while loop !!!!");
                        String data = mReader.readLine();
                        if(data == null){
                            System.out.println("we receive the null data!!!!!!!!!");
                            onlineFriend = false;
                        }else {
                            JSONObject json = new JSONObject(data);
                            System.out.println("the info which is from server: " + json);
                            sendMesInfo.socketType = json.getString("socketType");
                            sendMesInfo.username_from = json.getString("from");
                            sendMesInfo.username_to = json.getString("to");
                            sendMesInfo.sendMes = json.getString("msg");
                            msgArr.add(sendMesInfo);
                        }
                    }

                    Thread.sleep(200);

                    mWriter.close();
                    mReader.close();
                    mSocket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }*/

    public void startReceiveMsg() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    isStartRecieveMsg = true;
                    mSocket = new Socket(IP_ADDR, PORT);
                    mReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream(), "utf-8"));
                    mWriter = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream(), "utf-8"));

                   /* JSONObject json_out = new JSONObject();
                    json_out.put("to", msg.username_to);
                    json_out.put("msg", msg.sendMes);
                    json_out.put("from", msg.username_from);
                    json_out.put("socketType", msg.socketType);

                    System.out.println("the info which is gonna be sent to server: " + json_out);

                    mWriter.write(json_out.toString() + "\n");
                    //System.out.println("the info which is gonna be sent to server: " + mWriter);
                    mWriter.flush();*/
                    while (isStartRecieveMsg) {
                        System.out.println("we are in while loop !!!!");
                        String data = mReader.readLine();
                        if(data == null){
                            System.out.println("we receive the null data!!!!!!!!!");
                            onlineFriend = false;
                        }else {
                            JSONObject json = new JSONObject(data);
                            System.out.println("the info which is from server: " + json);
                            sendMesInfo.socketType = json.getString("socketType");
                            sendMesInfo.username_from = json.getString("from");
                            sendMesInfo.username_to = json.getString("to");
                            sendMesInfo.sendMes = json.getString("msg");
                            msgArr.add(sendMesInfo);
                        }
                    }

                    Thread.sleep(200);

                    mWriter.close();
                    mReader.close();
                    mSocket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    public ArrayList<String> getOnlineFriList() {
        ArrayList<String> online_frilist = new ArrayList<String>();
        try {
            //connect();
            String data = mReader.readLine();
            if (data == null) {
                onlineFriend = false;
            } else {
                JSONObject json = new JSONObject(data);
                JSONArray online_friArr = json.getJSONArray("online_friendlist");
                for (int i = 0; i < online_friArr.length(); i++) {
                    online_frilist.add(online_friArr.getString(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return online_frilist;
    }


    public void send(final SendMesInfo msg) {
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject json = new JSONObject();
                    json.put("to", msg.username_to);
                    json.put("msg", msg.sendMes);
                    json.put("from", msg.username_from);
                    json.put("socketType", msg.socketType);
                    mWriter.write(json.toString() + "\n");
                    System.out.println("the info which is gonna be sent to server: " + json);
                    mWriter.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    public void login(final SendMesInfo msg) {
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject json = new JSONObject();
                    json.put("to", msg.username_to);
                    json.put("msg", msg.sendMes);
                    json.put("from", msg.username_from);
                    json.put("socketType", msg.socketType);
                    mWriter.write(json.toString() + "\n");
                    mWriter.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
}

/*    static class SocketHandler extends Handler {

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
*/
