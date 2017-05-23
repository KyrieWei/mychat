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
        Holder_ima holder_ima = null;
        switch(mes.getType()){
            case myChatMessage.MessageType_from:
                convertView = LayoutInflater.from(mContext).inflate(R.layout.rec_mes_layout,null);
                holder = new Holder();
                holder.Content = (TextView)convertView.findViewById(R.id.rec_mes_view);
                holder.ava_view = (ImageView)convertView.findViewById(R.id.rec_avatar_view);
                convertView.setTag(holder);
                holder.Content.setText(mList.get(position).getContent());
                holder.ava_view.setImageBitmap(mList.get(position).bm);
                holder.ava_view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                break;
            case myChatMessage.MessageType_to:
                convertView = LayoutInflater.from(mContext).inflate(R.layout.send_mes_layout,null);
                holder = new Holder();
                holder.Content = (TextView)convertView.findViewById(R.id.send_mes_view);
                holder.ava_view = (ImageView)convertView.findViewById(R.id.send_avatar_view);
                convertView.setTag(holder);
                holder.Content.setText(mList.get(position).getContent());
                holder.ava_view.setImageBitmap(mList.get(position).bm);
                holder.ava_view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                break;
            case myChatMessage.MessageType_to_ima:
                convertView = LayoutInflater.from(mContext).inflate(R.layout.send_image_view,null);
                holder_ima = new Holder_ima();
                holder_ima.ava_view = (ImageView)convertView.findViewById(R.id.sendima_avatar_view);
                holder_ima.ima_view = (ImageView)convertView.findViewById(R.id.send_image_view);
                convertView.setTag(holder_ima);
                holder_ima.ava_view.setImageBitmap(mList.get(position).bm);
                holder_ima.ava_view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                holder_ima.ima_view.setImageBitmap(mList.get(position).image);
                holder_ima.ava_view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                break;
            case myChatMessage.MessageType_from_ima:
                convertView = LayoutInflater.from(mContext).inflate(R.layout.rec_image_view,null);
                holder_ima = new Holder_ima();
                holder_ima.ava_view = (ImageView)convertView.findViewById(R.id.recima_avatar_view);
                holder_ima.ima_view = (ImageView)convertView.findViewById(R.id.rec_image_view);
                convertView.setTag(holder_ima);
                holder_ima.ava_view.setImageBitmap(mList.get(position).bm);
                holder_ima.ava_view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                holder_ima.ima_view.setImageBitmap(mList.get(position).image);
                holder_ima.ava_view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                break;
        }
        return convertView;
    }

    class Holder {
        private TextView Content;
        private ImageView ava_view;
    }

    class Holder_ima{
        private ImageView ava_view;
        private ImageView ima_view;
    }

}
