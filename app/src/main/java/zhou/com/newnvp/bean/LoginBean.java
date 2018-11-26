package zhou.com.newnvp.bean;

/**
 * Created by zhou
 * on 2018/8/1.
 */

public class LoginBean {

    /**
     * Result : true
     * Message : 登录成功
     */

    private boolean Result;
    private String Message;

    public boolean isResult() {
        return Result;
    }

    public void setResult(boolean Result) {
        this.Result = Result;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "Result=" + Result +
                ", Message='" + Message + '\'' +
                '}';
    }
}
