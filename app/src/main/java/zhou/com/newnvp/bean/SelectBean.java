package zhou.com.newnvp.bean;

/**
 * Created by zhou
 * on 2018/8/2.
 */

public class SelectBean {

    private String user;//账号
    private String psd;//密码
    private boolean isRem;//记住账号
    private boolean isAuto;//自动登录

    public SelectBean(String user, String psd, boolean isRem, boolean isAuto) {
        this.user = user;
        this.psd = psd;
        this.isRem = isRem;
        this.isAuto = isAuto;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public boolean isRem() {
        return isRem;
    }

    public void setRem(boolean rem) {
        isRem = rem;
    }

    public boolean isAuto() {
        return isAuto;
    }

    public void setAuto(boolean auto) {
        isAuto = auto;
    }

    @Override
    public String toString() {
        return "SelectBean{" +
                "user='" + user + '\'' +
                ", psd='" + psd + '\'' +
                ", isRem=" + isRem +
                ", isAuto=" + isAuto +
                '}';
    }
}
