package kyrie.mychat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class chatView extends AppCompatActivity implements View.OnClickListener{

    ListView chatListView;
    EditText editMesView;
    Button sendMesBtn;
    ArrayList<myChatMessage> mData;
    MessageAdapter mAdapter;

    public SendMesInfo msg_to_send = new SendMesInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);

        chatListView = (ListView) findViewById(R.id.chatListView);
        editMesView = (EditText) findViewById(R.id.editMesView);
        editMesView.setMovementMethod(new ScrollingMovementMethod());
        sendMesBtn = (Button) findViewById(R.id.sendMesBtn);
        sendMesBtn.setOnClickListener(this);
        mData = new ArrayList<myChatMessage>();
        mData = LoadData();
        mAdapter = new MessageAdapter(this, mData);
        chatListView.setAdapter(mAdapter);
        chatListView.smoothScrollToPositionFromTop(mData.size(),0);
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
                //TODO:Send Mes
                String sendMes = editMesView.getText().toString().trim();
                msg_to_send.sendMes = sendMes;
                //startActivity(new Intent(this, MainListView.class));
                break;
        }
    }
}
