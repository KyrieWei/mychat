package kyrie.mychat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyrie_wei on 29/04/2017.
 */

public class MessageAdapter extends ArrayAdapter{
    public MessageAdapter(Context context, ArrayList<myChatMessage> mess) {
        super(context,0, mess);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            myChatMessage mes = (myChatMessage) getItem(position);
            switch(mes.getType()){
                case myChatMessage.MessageType_from:
                    if (convertView == null) {
                        convertView = LayoutInflater.from(getContext()).inflate(R.layout.rec_mes_layout, parent, false);
                    }
                    TextView Content = (TextView)convertView.findViewById(R.id.rec_mes_view);
                    Content.setText(mes.getContent());
                    ImageView ava_view = (ImageView)convertView.findViewById(R.id.rec_avatar_view);
                    ava_view.setImageResource(R.drawable.huaji);
                    break;
                case myChatMessage.MessageType_to:
                    if (convertView == null) {
                        convertView = LayoutInflater.from(getContext()).inflate(R.layout.send_mes_layout, parent, false);
                    }
                    Content = (TextView)convertView.findViewById(R.id.send_mes_view);
                    Content.setText(mes.getContent());
                    ava_view = (ImageView)convertView.findViewById(R.id.send_avatar_view);
                    ava_view.setImageResource(R.drawable.huaji);
                    break;
            }
            return convertView;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
