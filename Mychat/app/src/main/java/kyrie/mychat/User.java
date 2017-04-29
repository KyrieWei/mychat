package kyrie.mychat;

import android.media.Image;
import android.widget.ImageView;

import java.net.URL;

/**
 * Created by Kyrie_wei on 28/04/2017.
 */

public class User {
    public String userName;
    public String avatarUrl_Str;

    public User(String userName, String avatarUrl_Str){
        this.userName = userName;
        this.avatarUrl_Str = avatarUrl_Str;
    }
}
