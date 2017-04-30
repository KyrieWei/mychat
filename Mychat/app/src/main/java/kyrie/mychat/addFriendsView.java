package kyrie.mychat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class addFriendsView extends AppCompatActivity implements View.OnClickListener {

    private Button SearchFriBtn;
    private String seaUserName;
    private EditText SearchUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends_view);

        SearchFriBtn = (Button) findViewById(R.id.searchFriBtn);
        SearchUserName = (EditText) findViewById(R.id.SearchUserName);

        SearchFriBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.searchFriBtn:
                //ruquest data
                startActivity(new Intent(this,searchResView.class));
                break;
        }
    }
}
