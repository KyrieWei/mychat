package kyrie.mychat;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import android.os.Handler;

public class chatView extends AppCompatActivity implements View.OnClickListener{

    ListView chatListView;
    EditText editMesView;
    Button sendMesBtn, sendImaBtn;
    public ArrayList<myChatMessage> mData = new ArrayList<myChatMessage>();;

    public chatViewAdapter CVAdapter;

    public String my_name;
    public String friend_name;
    public String my_ava;
    public String friend_ava;
    public Bitmap bm_myava;
    public Bitmap bm_friendava;
    public String image_path;
    public String socketType = "sendMes";
    public SendMesInfo msg_to_send = new SendMesInfo();
    public SendMesSocket sendMesSocket = SendMesSocket.getInstance();
    public ArrayList<SendMesInfo> msgArr = new ArrayList<SendMesInfo>();
    public byte[] ava_array;
    public Handler handler = new Handler();
    public mHandler mhandler = new mHandler();

    public Client client_CV = new Client();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);

        chatListView = (ListView) findViewById(R.id.chatListView);
        editMesView = (EditText) findViewById(R.id.editMesView);
        editMesView.setMovementMethod(new ScrollingMovementMethod());
        sendMesBtn = (Button) findViewById(R.id.sendMesBtn);
        sendMesBtn.setOnClickListener(this);
        sendImaBtn = (Button) findViewById(R.id.sendImageBtn);
        sendImaBtn.setOnClickListener(this);

        CVAdapter = new chatViewAdapter(mData,this);
        chatListView.setAdapter(CVAdapter);
        chatListView.smoothScrollToPositionFromTop(mData.size(),0);
        Intent intent = getIntent();
        friend_name = intent.getStringExtra("sendMsg_to");
        my_name = intent.getStringExtra("my_name");

        for(int i = 0; i < client_CV.friendList.size(); i ++){
            if(client_CV.friendList.get(i).friend_name.equals(my_name)){
                my_ava = client_CV.friendList.get(i).friend_ava_url;
            }
            if(client_CV.friendList.get(i).friend_name.equals((friend_name))){
                friend_ava = client_CV.friendList.get(i).friend_ava_url;
            }
        }

        byte[] my_avatar = Base64.decode(my_ava, Base64.DEFAULT);
        bm_myava = BitmapFactory.decodeByteArray(my_avatar,0,my_avatar.length);
        byte[] friend_avatar = Base64.decode(friend_ava, Base64.DEFAULT);
        bm_friendava = BitmapFactory.decodeByteArray(friend_avatar,0,friend_avatar.length);

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
                    myChatMessage mMessage;
                    if(sendMesSocket.msgArr.get(i).socketType.equals("sendIma")){
                        byte[] ima = Base64.decode(msgArr.get(i).sendMes,Base64.DEFAULT);
                        Bitmap bm = BitmapFactory.decodeByteArray(ima,0,ima.length);
                        mMessage = new myChatMessage(myChatMessage.MessageType_from_ima,bm,bm_friendava);
                    }else {
                        mMessage = new myChatMessage(myChatMessage.MessageType_from, msgArr.get(i).sendMes, bm_friendava);
                    }
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendMesBtn:
                sendMes();
                editMesView.setText("");
                break;
            case R.id.sendImageBtn:
                sendIma();
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

            myChatMessage Message = new myChatMessage(myChatMessage.MessageType_to, sendMes,bm_myava);
            mData.add(Message);
            CVAdapter.refresh(mData);

        }

    }

    protected void sendIma() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ContentResolver resolver = getContentResolver();
        if (requestCode == 0) {
            Uri uri = data.getData();
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            image_path = cursor.getString(column_index);
            System.out.println("the path of image is : " + image_path);

            Bitmap ima_to_send = getLocalIma(image_path);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ima_to_send.compress(Bitmap.CompressFormat.PNG, 100, stream);
            ava_array = stream.toByteArray();
            msg_to_send.sendMes = Base64.encodeToString(ava_array,Base64.DEFAULT);
            msg_to_send.socketType = "sendIma";
            msg_to_send.username_from = my_name;
            msg_to_send.username_to = friend_name;
            sendMesSocket.send(msg_to_send);

            myChatMessage Message = new myChatMessage(myChatMessage.MessageType_to_ima,ima_to_send,bm_myava);
            mData.add(Message);
            CVAdapter.refresh(mData);
        }
    }

    public static Bitmap getLocalIma(String path){
        try{
            FileInputStream ima = new FileInputStream(path);
            return BitmapFactory.decodeStream(ima);
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }
}
