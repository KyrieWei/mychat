package kyrie.mychat;

import android.content.Intent;
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

public class chatView extends AppCompatActivity implements View.OnClickListener{

    ListView chatListView;
    EditText editMesView;
    Button sendMesBtn;
    ArrayList<myChatMessage> mData = new ArrayList<myChatMessage>();;
    MessageAdapter mAdapter;

    public String my_name;
    public String friend_name;
    public String socketType = "sendMes";
    public SendMesInfo msg_to_send = new SendMesInfo();
    public SendMesSocket sendMesSocket = SendMesSocket.getInstance();
    public ArrayList<SendMesInfo> msgArr = new ArrayList<SendMesInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);

        chatListView = (ListView) findViewById(R.id.chatListView);
        editMesView = (EditText) findViewById(R.id.editMesView);
        editMesView.setMovementMethod(new ScrollingMovementMethod());
        sendMesBtn = (Button) findViewById(R.id.sendMesBtn);
        sendMesBtn.setOnClickListener(this);
        //mData = new ArrayList<myChatMessage>();
        //mData = LoadData();
        mAdapter = new MessageAdapter(this, mData);
        chatListView.setAdapter(mAdapter);
        chatListView.smoothScrollToPositionFromTop(mData.size(),0);

    }

    public void getuserInfo(String user_from, String user_to){
        msg_to_send.username_from = user_from;
        msg_to_send.username_to = user_to;
    }

    public void getNewInfo(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    int k =  sendMesSocket.msgArr.size() - msgArr.size();
                    if( k != 0){
                        for(int i = msgArr.size(); i < sendMesSocket.msgArr.size(); i ++ ){
                            msgArr.add(sendMesSocket.msgArr.get(i));
                            myChatMessage Message = new myChatMessage(myChatMessage.MessageType_from,msgArr.get(i).sendMes);
                            mData.add(Message);
                            startActivity(getIntent());
                        }
                    }
                }
            }
        });
        thread.start();
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
            sendMesSocket.startReceiveMsg(msg_to_send);
            //sendMesSocket.send();

            myChatMessage Message = new myChatMessage(myChatMessage.MessageType_to, sendMes);
            mData.add(Message);
            this.finish();
            startActivity(getIntent());
        }

    }
}
