package kyrie.mychat;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.attr.onClick;
import static kyrie.mychat.R.id.add;
import static kyrie.mychat.R.id.parent;

public class MainListView extends AppCompatActivity {

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
        User item_user = new User("dalao", avaurl);
        ArrayList<User> arrayOfUsers = new ArrayList<User>();
        for (int i = 0; i < 20; i ++){
            arrayOfUsers.add(item_user);
        }
        UserAdapter adapter = new UserAdapter(this,arrayOfUsers);
        final ListView friListView = (ListView) findViewById(R.id.FriendsListView);

        friListView.setAdapter(adapter);

        friListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainListView.this, chatView.class);
                startActivity(intent);
            }
        });

    }
}
