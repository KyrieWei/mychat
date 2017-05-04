package kyrie.mychat;

import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.Socket;

public class LoginView extends AppCompatActivity implements View.OnClickListener{

    private Button loginBtn;
    private EditText etUserName, etPassword;
    private TextView tvRegisterLink;
    private Client connector;
    private String DstName = "23.106.150.31";
    private int DstPort = 4000;
    private HandlerThread handlerThread;
    private Handler handler;

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
        handlerThread = new HandlerThread("LoginView");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());

        connector = new Client();
        loginBtn.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);
    }


    @Override
    public  void onClick(View v){
        switch(v.getId()){
            case R.id.signInBtn:

                final String Username = etUserName.getText().toString().trim();
                final String Password = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(Username)){
                    Toast.makeText(this, "User Name Cannot Be Empty!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Password)){
                    Toast.makeText(this, "Password Cannot Be Empty!",Toast.LENGTH_SHORT).show();
                    return;
                }
                connector.sendUserInfo(Username,Password);
                //TODO: login in
                break;
            case R.id.RegisterLink:
                startActivity(new Intent(this,RegisterView.class));
                break;
        }
    }

}
