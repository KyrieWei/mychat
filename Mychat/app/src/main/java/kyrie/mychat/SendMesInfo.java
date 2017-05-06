package kyrie.mychat;

/**
 * Created by kyrie on 2017/5/6.
 */

public class SendMesInfo {
    private String username_from;
    private String username_to;
    private String user_from_ip;
    private String sendMes;

    public void SendMesInfo(String username_from, String username_to, String user_from_ip,String sendMes){
        this.username_from = username_from;
        this.username_to = username_to;
        this.user_from_ip = user_from_ip;
        this.sendMes = sendMes;
    }
}
