package kyrie.mychat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;

public class LoginView extends AppCompatActivity implements View.OnClickListener {

    Button loginBtn;
    EditText etUserName, etPassword;
    TextView tvRegisterLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);

        loginBtn = (Button) findViewById(R.id.signInBtn);
        etUserName = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvRegisterLink = (TextView) findViewById(R.id.RegisterLink);

        loginBtn.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);
    }

    @Override
    public  void onClick(View v){
        switch(v.getId()){
            case R.id.signInBtn:
                startActivity(new Intent(this,MainListView.class));
                break;
            case R.id.RegisterLink:
                startActivity(new Intent(this,RegisterView.class));
                break;
        }
    }
}
