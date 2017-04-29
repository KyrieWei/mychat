package kyrie.mychat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Kyrie_wei on 28/04/2017.
 */

public class UserAdapter extends ArrayAdapter<User> {
    public UserAdapter(Context context, ArrayList<User> users) {
        super(context,0, users);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.customusercell, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        tvName.setText(user.userName);
        ImageView tvAvatar = (ImageView) convertView.findViewById(R.id.tvAvatar);
        //new ImageLoad(user.avatarUrl_Str,tvAvatar).execute();
        tvAvatar.setImageResource(R.drawable.huaji);
        return convertView;
    }

}
