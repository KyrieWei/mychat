package kyrie.mychat;

import android.content.Intent;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.os.Handler;

public class chatView extends AppCompatActivity implements View.OnClickListener{

    ListView chatListView;
    EditText editMesView;
    Button sendMesBtn;
    public ArrayList<myChatMessage> mData = new ArrayList<myChatMessage>();;

    public chatViewAdapter CVAdapter;

    public String my_name;
    public String friend_name;
    public String socketType = "sendMes";
    public SendMesInfo msg_to_send = new SendMesInfo();
    public SendMesSocket sendMesSocket = SendMesSocket.getInstance();
    public ArrayList<SendMesInfo> msgArr = new ArrayList<SendMesInfo>();
    public Handler handler = new Handler();
    public mHandler mhandler = new mHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);

        chatListView = (ListView) findViewById(R.id.chatListView);
        editMesView = (EditText) findViewById(R.id.editMesView);
        editMesView.setMovementMethod(new ScrollingMovementMethod());
        sendMesBtn = (Button) findViewById(R.id.sendMesBtn);
        sendMesBtn.setOnClickListener(this);

        //mData = LoadData();

        CVAdapter = new chatViewAdapter(mData,this);
        chatListView.setAdapter(CVAdapter);
        chatListView.smoothScrollToPositionFromTop(mData.size(),0);
        Intent intent = getIntent();
        friend_name = intent.getStringExtra("sendMsg_to");
        my_name = intent.getStringExtra("my_name");

        handler.postDelayed(update,200);

        msg_to_send.sendMes = " ";
        msg_to_send.username_to = " ";
        msg_to_send.username_from = my_name;
        msg_to_send.socketType = "login";
        sendMesSocket.send(msg_to_send);

    }
    Runnable update = new Runnable() {
        @Override
        public void run() {
            int k =  sendMesSocket.msgArr.size() - msgArr.size();
            if( k != 0){
                for(int i = msgArr.size(); i < sendMesSocket.msgArr.size(); i ++ ){
                    msgArr.add(sendMesSocket.msgArr.get(i));
                    myChatMessage mMessage = new myChatMessage(myChatMessage.MessageType_from,msgArr.get(i).sendMes);
                    mhandler.obtainMessage(0,mMessage).sendToTarget();
                }
            }
            handler.postDelayed(this,200);
        }
    };


     class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mData.add((myChatMessage)msg.obj);
                    CVAdapter.refresh(mData);
                    break;
                default:
                    break;
            }
        }
    }

    public void getuserInfo(String user_from, String user_to){
        msg_to_send.username_from = user_from;
        msg_to_send.username_to = user_to;
    }


    private ArrayList<myChatMessage> LoadData() {
        ArrayList<myChatMessage> Messages = new ArrayList<myChatMessage>();
        myChatMessage Message = new myChatMessage(myChatMessage.MessageType_from,"hello!");
        Messages.add(Message);
        Message = new myChatMessage(myChatMessage.MessageType_to,"what's up?");
        Messages.add(Message);
        Message = new myChatMessage(myChatMessage.MessageType_from,"I wanna ask you a question?");
        Messages.add(Message);
        Message = new myChatMessage(myChatMessage.MessageType_to,"Yeah? What's that? and how are you today? is your finger painful?");
        Messages.add(Message);
        Message = new myChatMessage(myChatMessage.MessageType_from,"hello!");
        Messages.add(Message);
        Message = new myChatMessage(myChatMessage.MessageType_to,"what's up?");
        Messages.add(Message);
        Message = new myChatMessage(myChatMessage.MessageType_from,"I wanna ask you a question?");
        Messages.add(Message);
        Message = new myChatMessage(myChatMessage.MessageType_to,"Yeah? What's that? and how are you today? is your finger painful?");
        Messages.add(Message);
        Message = new myChatMessage(myChatMessage.MessageType_from,"hello!");
        Messages.add(Message);
        Message = new myChatMessage(myChatMessage.MessageType_to,"what's up?");
        Messages.add(Message);
        Message = new myChatMessage(myChatMessage.MessageType_from,"I wanna ask you a question?");
        Messages.add(Message);
        Message = new myChatMessage(myChatMessage.MessageType_to,"Yeah? What's that? and how are you today? is your finger painful?");
        Messages.add(Message);
        return  Messages;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendMesBtn:
                sendMes();
                editMesView.setText("");
                break;
        }
    }

    public void sendMes(){
        String sendMes = editMesView.getText().toString().trim();
        if(TextUtils.isEmpty(sendMes)){
            Toast.makeText(this, "Content Cannot Be Empty!",Toast.LENGTH_SHORT).show();
        }else {
            msg_to_send.sendMes = sendMes;
            msg_to_send.socketType = socketType;
            msg_to_send.username_to = friend_name;
            msg_to_send.username_from = my_name;
            sendMesSocket.send(msg_to_send);

            myChatMessage Message = new myChatMessage(myChatMessage.MessageType_to, sendMes);
            mData.add(Message);
            CVAdapter.refresh(mData);

            System.out.println("the size of message array is : " + mData.size());
            for(myChatMessage item : mData){
                System.out.println("the message of the type is : " + item.mtype + " and the content is " + item.mContent);
            }
        }

    }
}
