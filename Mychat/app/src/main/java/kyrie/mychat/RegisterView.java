package kyrie.mychat;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterView extends AppCompatActivity implements View.OnClickListener {

    EditText etNewUserName, etNewPassword, etConfNewPsw;
    Button registerBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);

        etNewUserName = (EditText) findViewById(R.id.etNewUsername);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etConfNewPsw = (EditText) findViewById(R.id.etConNewPassword);
        registerBtn = (Button) findViewById(R.id.RegisterBtn);

        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.RegisterBtn:

                break;
        }
    }
}
