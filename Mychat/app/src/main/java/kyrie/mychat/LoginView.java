package kyrie.mychat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class LoginView extends AppCompatActivity implements View.OnClickListener{

    private Button loginBtn;
    private EditText etUserName, etPassword;
    private TextView tvRegisterLink;
    private Client connector;
    private String DstName = "23.106.150.31";
    private int DstPort = 4000;
    private HandlerThread handlerThread;
    private Handler handler;

    private loginUser client_user;

    public SendMesInfo sendMesInfo;
    private static SendMesSocket sendMesSocket = SendMesSocket.getInstance();
    public ArrayList<friendInfo> friendInfoArrayList = new ArrayList<friendInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);

        initView();
        initData();
    }

    private void initView(){
        loginBtn = (Button) findViewById(R.id.signInBtn);
        etUserName = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvRegisterLink = (TextView) findViewById(R.id.RegisterLink);
    }

    private void initData(){
        connector = new Client();
        sendMesInfo = new SendMesInfo();
        //sendMesSocket = (SendMesSocket
        loginBtn.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);
    }


    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
                        return ip;
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public  void onClick(View v){
        switch(v.getId()){
            case R.id.signInBtn:
                String Username = etUserName.getText().toString().trim();
                String Password = etPassword.getText().toString().trim();
                //String IP_ADDR = getLocalIpAddress();
                String type = "login";
                client_user = new loginUser(Username,Password,type);

                if (TextUtils.isEmpty(Username)){
                    Toast.makeText(this, "registerUser Name Cannot Be Empty!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Password)){
                    Toast.makeText(this, "Password Cannot Be Empty!",Toast.LENGTH_SHORT).show();
                    return;
                }

                connector.LoginRequest(client_user);
                while(true){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(connector.loading){
                        break;
                    }

                }
                System.out.println("!!!!!!!!!!is Success:"  + connector.isSuccess);

                if(connector.isSuccess.equals(connector.successed)){
                    Toast.makeText(this, "Login successfully ",Toast.LENGTH_SHORT).show();
                    sendMesSocket.startReceiveMsg();
                    friendInfoArrayList = connector.friendList;
                    Intent intent = new Intent(this, MainListView.class);
                    intent.putExtra("my_name", Username);
                    startActivity(intent);
                }else if(connector.isSuccess.equals(connector.username_not_exist)){
                    Toast.makeText(this,"Login Failed! The user does not exist!",Toast.LENGTH_SHORT).show();
                }else if(connector.isSuccess.equals(connector.server_failed)){
                    Toast.makeText(this, "Server has been closed! ",Toast.LENGTH_SHORT).show();
                }else if(connector.isSuccess.equals(connector.password_wrong)){
                    Toast.makeText(this, "Password is wrong! ",Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.RegisterLink:
                startActivity(new Intent(this,RegisterView.class));
                break;
        }
    }

}
