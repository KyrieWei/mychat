package kyrie.mychat;

import android.media.Image;
import android.widget.ImageView;

import java.net.URL;

/**
 * Created by Kyrie_wei on 28/04/2017.
 */

public class registerUser {
    public String userName;
    public String passWord;
    public String type;
    public String avatarUrl_Str;

    public registerUser(String userName, String passWord, String type, String avatarUrl_Str){
        this.userName = userName;
        this.avatarUrl_Str = avatarUrl_Str;
        this.passWord = passWord;
        this.type = type;
    }
}
