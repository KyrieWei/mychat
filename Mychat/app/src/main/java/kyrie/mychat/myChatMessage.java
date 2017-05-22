package kyrie.mychat;

import android.graphics.Bitmap;

/**
 * Created by Kyrie_wei on 29/04/2017.
 */

public class myChatMessage {
    public static final int MessageType_from=1;
    public static final int MessageType_to=2;
    public static final int MessageType_to_ima = 3;
    public static final int MessageType_from_ima = 4;

    public int mtype;
    public String mContent;
    public Bitmap bm;
    public Bitmap image;

    public myChatMessage(int type, String content, Bitmap bm){
        this.mtype = type;
        this.mContent = content;
        this.bm = bm;
    }

    public myChatMessage(int type, Bitmap image, Bitmap bm){
        this.mtype = type;
        this.image = image;
        this.bm = bm;
    }

    public int getType(){
        return mtype;
    }

    public void setType(int mtype){
        this.mtype = mtype;
    }

    public String getContent(){
        return mContent;
    }

    public void setContent(String mContent){
        this.mContent = mContent;
    }

}
