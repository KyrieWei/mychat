package kyrie.mychat;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import static android.R.attr.onClick;
import static kyrie.mychat.R.id.add;
import static kyrie.mychat.R.id.parent;

public class MainListView extends AppCompatActivity {

    public String my_name;
    public ArrayList<String> friendNameArr = new ArrayList<String>();
    public ArrayList<String> friend_online = new ArrayList<String>();
    private long firstTime = 0;

    private static SendMesSocket sendMesSocket = SendMesSocket.getInstance();
    public SendMesInfo sendMesInfo;
    public boolean isOnline;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_friend,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_friends:
                startActivity(new Intent(this,addFriendsView.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list_view);

        String avaurl = "https://api.learn2crack.com/android/images/donut.png";

        Intent intent = getIntent();
        my_name = intent.getStringExtra("my_name");
        friendNameArr = intent.getStringArrayListExtra("friendlist");

        //sendMesSocket = (SendMesSocket)getApplication();
        sendMesInfo = new SendMesInfo();

        ArrayList<friendInfo> arrayOfUsers = new ArrayList<friendInfo>();

        for (int i = 0; i < friendNameArr.size(); i ++){
            friendInfo friend_item = new friendInfo(friendNameArr.get(i),avaurl);
            arrayOfUsers.add(friend_item);
        }

        UserAdapter adapter = new UserAdapter(this,arrayOfUsers);
        final ListView friListView = (ListView) findViewById(R.id.FriendsListView);

        friListView.setAdapter(adapter);

        friListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String sendMesTo_name = friendNameArr.get(position);
                sendMesInfo.username_from = my_name;
                sendMesInfo.username_to = sendMesTo_name;
                sendMesInfo.sendMes = " ";
                sendMesInfo.socketType = "startchat";
                sendMesSocket.startReceiveMsg(sendMesInfo);
                //sendMesSocket.send();
                friend_online = sendMesSocket.online_frilist;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("!!!!!!!!!!!the friends who are online: " + friend_online);
                isOnline = false;
                for(String item : friend_online){
                    if(item.equals(sendMesTo_name)){
                        isOnline = true;
                    }
                }
                if(isOnline) {
                    Intent intent = new Intent(MainListView.this, MainListView.class);
                    intent.putExtra("sendMsg_to", sendMesTo_name);
                    intent.putExtra("my_name", my_name);
                    startActivity(intent);
                }else {
                    Toast.makeText(MainListView.this, "Sorry, he / she is not online!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(MainListView.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                System.exit(0);
            }
        }
        return super.onKeyUp(keyCode, event);
    }

}
