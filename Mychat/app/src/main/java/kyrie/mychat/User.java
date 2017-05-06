package kyrie.mychat;

import android.media.Image;
import android.widget.ImageView;

import java.net.URL;

/**
 * Created by Kyrie_wei on 28/04/2017.
 */

public class User {
    public String userName;
    public String IP_ADDR;
    public int PORT;
    public String passWord;
    public String type;
    //public String avatarUrl_Str;

    public User(String userName, String IP_ADDR, int PORT, String passWord, String type){
        this.userName = userName;
        //this.avatarUrl_Str = avatarUrl_Str;
        this.passWord = passWord;
        this.IP_ADDR = IP_ADDR;
        this.PORT = PORT;
        this.type = type;
    }
}
