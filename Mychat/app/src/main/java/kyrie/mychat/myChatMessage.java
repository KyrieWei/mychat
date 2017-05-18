package kyrie.mychat;

/**
 * Created by Kyrie_wei on 29/04/2017.
 */

public class myChatMessage {
    public static final int MessageType_from=1;
    public static final int MessageType_to=2;

    public int mtype;
    public String mContent;

    public myChatMessage(int type, String content){
        this.mtype = type;
        this.mContent = content;
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
