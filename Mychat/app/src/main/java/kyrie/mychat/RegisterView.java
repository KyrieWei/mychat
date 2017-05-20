package kyrie.mychat;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RegisterView extends AppCompatActivity implements View.OnClickListener {

    public final int REQUEST_CODE_PICK_IMAGE = 0;

    EditText etNewUserName, etNewPassword, etConfNewPsw;
    Button registerBtn;
    Client sendRegInfo;
    ImageView myAvatar;
    public registerUser user_register;
    public String image_path;
    public byte[] ava_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);

        etNewUserName = (EditText) findViewById(R.id.etNewUsername);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etConfNewPsw = (EditText) findViewById(R.id.etConNewPassword);
        registerBtn = (Button) findViewById(R.id.RegisterBtn);
        registerBtn.setOnClickListener(this);
        myAvatar = (ImageView) findViewById(R.id.imaLoad_view);
        myAvatar.setOnClickListener(this);
        sendRegInfo = new Client();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.RegisterBtn:
                //Thread thread = new Thread();
                final String newUserName = etNewUserName.getText().toString().trim();
                final String newPassword = etNewPassword.getText().toString().trim();
                final String conNewPassword = etConfNewPsw.getText().toString().trim();
                final String type = "register";

                if (TextUtils.isEmpty(newUserName)){
                    Toast.makeText(this, "registerUser Name Cannot Be Empty!",Toast.LENGTH_SHORT).show();
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


                if(ava_array == null){
                    Toast.makeText(this, "Please select a picture as your avatar!",Toast.LENGTH_SHORT).show();
                    return;
                }

                String avaByteArr = Base64.encodeToString(ava_array, Base64.DEFAULT);
                user_register = new registerUser(newUserName, newPassword,type,avaByteArr);

                sendRegInfo.registerRequest(user_register);
                //sendRegInfo.sendUserInfo(newUserName,newPassword,type);
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
                    Toast.makeText(this, "registerUser name has been registered! ",Toast.LENGTH_SHORT).show();
                }else if(sendRegInfo.isSuccess.equals(sendRegInfo.server_failed)){
                    Toast.makeText(this, "Server has been closed! ",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imaLoad_view:
                getImageFromAlbum();
        }

    }

    protected void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bm = null;
        ContentResolver resolver = getContentResolver();
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
                Uri uri = data.getData();
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(uri, proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                image_path = cursor.getString(column_index);
                System.out.println("the path of image is : " + image_path);
                bm = getLocalIma(image_path);
                myAvatar.setImageBitmap(bm);
                myAvatar.setScaleType(ImageView.ScaleType.CENTER_CROP);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                ava_array = stream.toByteArray();
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
