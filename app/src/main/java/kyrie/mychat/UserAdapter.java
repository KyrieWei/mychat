package kyrie.mychat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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

public class UserAdapter extends ArrayAdapter<friendInfo> {
    public UserAdapter(Context context, ArrayList<friendInfo> users) {
        super(context,0, users);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        friendInfo user = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.customusercell, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        tvName.setText(user.friend_name);
        ImageView tvAvatar = (ImageView) convertView.findViewById(R.id.tvAvatar);
        byte[] avatar = Base64.decode(user.friend_ava_url, Base64.DEFAULT);
        Bitmap bm = BitmapFactory.decodeByteArray(avatar,0,avatar.length);
        tvAvatar.setImageBitmap(bm);
        tvAvatar.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return convertView;
    }

}
