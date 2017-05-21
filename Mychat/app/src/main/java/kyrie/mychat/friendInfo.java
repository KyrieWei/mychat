package kyrie.mychat;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kyrie on 2017/5/6.
 */

public class friendInfo implements Parcelable {
    public String friend_name;
    public String friend_ava_url;

    public friendInfo(){}

    public friendInfo(String friend_name, String friend_ava_url){
        this.friend_name = friend_name;
        this.friend_ava_url = friend_ava_url;
    }

    public friendInfo(Parcel source){
        friend_name = source.readString();
        friend_ava_url = source.readString();
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(friend_name);
        dest.writeString(friend_ava_url);
    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public friendInfo createFromParcel(Parcel in) {
            return new friendInfo(in);
        }

        public friendInfo[] newArray(int size) {
            return new friendInfo[size];
        }
    };

}
