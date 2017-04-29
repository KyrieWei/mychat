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

public class MessageAdapter extends BaseAdapter{

    private Context mContext;
    private List<myChatMessage> mData;

    public MessageAdapter(Context context, List<myChatMessage> data){
        this.mContext = context;
        this.mData = data;
    }

    public void Refresh(){
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView Content;
        ImageView ava_view;
            switch(mData.get(position).getType()){
                case myChatMessage.MessageType_from:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.rec_mes_layout,null);
                    Content = (TextView)convertView.findViewById(R.id.rec_mes_view);
                    Content.setText(mData.get(position).getContent());
                    ava_view = (ImageView)convertView.findViewById(R.id.rec_avatar_view);
                    ava_view.setImageResource(R.drawable.huaji);
                    break;
                case myChatMessage.MessageType_to:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.send_mes_layout,null);
                    Content = (TextView)convertView.findViewById(R.id.send_mes_view);
                    Content.setText(mData.get(position).getContent());
                    ava_view = (ImageView)convertView.findViewById(R.id.rec_avatar_view);
                    ava_view.setImageResource(R.drawable.huaji);
                    break;
            }
            return convertView;
    }
}
