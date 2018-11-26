package zhou.com.newnvp.bean;

/**
 * Created by zhou
 * on 2018/8/1.
 */

public class VpnSelectBean {
    private String vpnUser;
    private String vpnPsd;
    private boolean isSelect;
    private boolean isYes;//是
    private boolean isNo;//否

    public VpnSelectBean(String vpnUser, String vpnPsd, boolean isSelect, boolean isYes, boolean isNo) {
        this.vpnUser = vpnUser;
        this.vpnPsd = vpnPsd;
        this.isSelect = isSelect;
        this.isYes = isYes;
        this.isNo = isNo;
    }

    public String getVpnUser() {
        return vpnUser;
    }

    public void setVpnUser(String vpnUser) {
        this.vpnUser = vpnUser;
    }

    public String getVpnPsd() {
        return vpnPsd;
    }

    public void setVpnPsd(String vpnPsd) {
        this.vpnPsd = vpnPsd;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isYes() {
        return isYes;
    }

    public void setYes(boolean yes) {
        isYes = yes;
    }

    public boolean isNo() {
        return isNo;
    }

    public void setNo(boolean no) {
        isNo = no;
    }

    @Override
    public String toString() {
        return "VpnSelectBean{" +
                "vpnUser='" + vpnUser + '\'' +
                ", vpnPsd='" + vpnPsd + '\'' +
                ", isSelect=" + isSelect +
                ", isYes=" + isYes +
                ", isNo=" + isNo +
                '}';
    }
}
