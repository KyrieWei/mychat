package kyrie.mychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainListView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list_view);

        ArrayList<User> arrayOfUsers = new ArrayList<User>();

        UserAdapter adapter = new UserAdapter(this,arrayOfUsers);

        ListView friListView = (ListView) findViewById(R.id.FriendsListView);
        friListView.setAdapter(adapter);

        User item_user = new User("dalao");
        for (int i = 0; i < 20; i ++){
            arrayOfUsers.add(item_user);
        }
       /* ListView friendsList = (ListView) findViewById(R.id.FriendsListView);
        ArrayList<String> friLst = new ArrayList<String>();
        for (int i = 0; i < 20; i ++) {
            friLst.add("dalao " + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainListView.this,
                android.R.layout.simple_list_item_1, friLst);
        friendsList.setAdapter(adapter);

        friendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView friendName = (TextView) view;
                System.out.println(friendName.getText().toString());
            }
        });*/
    }
}
