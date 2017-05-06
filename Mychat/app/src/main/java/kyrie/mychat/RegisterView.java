package kyrie.mychat;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterView extends AppCompatActivity implements View.OnClickListener {

    EditText etNewUserName, etNewPassword, etConfNewPsw;
    Button registerBtn;
    Client sendRegInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);

        etNewUserName = (EditText) findViewById(R.id.etNewUsername);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etConfNewPsw = (EditText) findViewById(R.id.etConNewPassword);
        registerBtn = (Button) findViewById(R.id.RegisterBtn);
        registerBtn.setOnClickListener(this);
        sendRegInfo = new Client();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.RegisterBtn:
                Thread thread = new Thread();
                final String newUserName = etNewUserName.getText().toString().trim();
                final String newPassword = etNewPassword.getText().toString().trim();
                final String conNewPassword = etConfNewPsw.getText().toString().trim();

                if (TextUtils.isEmpty(newUserName)){
                    Toast.makeText(this, "User Name Cannot Be Empty!",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(newPassword)){
                    Toast.makeText(this, "Password Cannot Be Empty!",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(conNewPassword)){
                    Toast.makeText(this, "Confirm Password Cannot Be Empty!",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (conNewPassword.equals(newPassword)== false){
                    Toast.makeText(this, "Confirm Password Is Inconsistent!",Toast.LENGTH_SHORT).show();
                    return;
                }

                sendRegInfo.sendUserInfo(newUserName,newPassword,"register");

                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("!!!!!!!!!!!!is Success: " + sendRegInfo.isSuccess);
                if(sendRegInfo.isSuccess.equals(sendRegInfo.successed)){
                    Toast.makeText(this, "Successfully Registered!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this,LoginView.class));
                }else if(sendRegInfo.isSuccess.equals(sendRegInfo.username_duplicated)){
                    Toast.makeText(this, "User name has been registered! ",Toast.LENGTH_SHORT).show();
                }else if(sendRegInfo.isSuccess.equals(sendRegInfo.server_failed)){
                    Toast.makeText(this, "Server has been closed! ",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
