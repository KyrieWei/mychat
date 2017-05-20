package kyrie.mychat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by kyrie on 2017/5/19.
 */

public class chatViewAdapter extends BaseAdapter {
    private ArrayList<myChatMessage> mList;
    private Context mContext;

    public chatViewAdapter(ArrayList<myChatMessage> list, Context context){
        mList = list;
        mContext = context;
    }

    public void refresh(ArrayList<myChatMessage> list){
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount(){
        return mList.size();
    }

    @Override
    public Object getItem(int postion){
        return mList.get(postion);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        myChatMessage mes = (myChatMessage) getItem(position);
        System.out.println("the message in getView function is : " + mes.mContent + " type is : " + mes.mtype);
        Holder holder = null;
        switch(mes.getType()){
            case myChatMessage.MessageType_from:
                //if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.rec_mes_layout,null);
                    holder = new Holder();
                    holder.Content = (TextView)convertView.findViewById(R.id.rec_mes_view);
                    holder.ava_view = (ImageView)convertView.findViewById(R.id.rec_avatar_view);
                    convertView.setTag(holder);
                //}else{
                    //holder = (Holder)convertView.getTag();
                //}
                holder.Content.setText(mList.get(position).getContent());
                holder.ava_view.setImageResource(R.drawable.huaji);
                break;
            case myChatMessage.MessageType_to:
                //if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.send_mes_layout,null);
                    holder = new Holder();
                    holder.Content = (TextView)convertView.findViewById(R.id.send_mes_view);
                    holder.ava_view = (ImageView)convertView.findViewById(R.id.send_avatar_view);
                    convertView.setTag(holder);
                //}else{
                    //holder = (Holder)convertView.getTag();
                //}
                holder.Content.setText(mList.get(position).getContent());
                holder.ava_view.setImageResource(R.drawable.huaji);
                break;
        }
        return convertView;
    }

    class Holder {
        private TextView Content;
        private ImageView ava_view;
    }

}
