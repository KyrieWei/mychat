package kyrie.mychat;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class searchResView extends AppCompatActivity implements View.OnClickListener{

    private Button addRequestBtn;
    private ImageView requestUserImg;
    private TextView requestUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_res_view);

        addRequestBtn = (Button) findViewById(R.id.askForFriBtn);
        requestUserImg = (ImageView) findViewById(R.id.seaResUserImg);
        requestUserName = (TextView) findViewById(R.id.seaResUserName);

        addRequestBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.askForFriBtn:
                //do something
                break;
        }
    }
}
