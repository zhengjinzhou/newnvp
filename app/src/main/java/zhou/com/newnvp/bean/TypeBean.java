package zhou.com.newnvp.bean;

/**
 * Created by zhou
 * on 2018/10/10.
 */

public class TypeBean {
    /**
     * type : EditDocument
     * params : {"guid":"F44DA8AB6A004DEC9553760BD5896801","path":"","name":"陈伟强"}
     */

    private String type;
    private ParamsBean params;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public static class ParamsBean {
        /**
         * guid : F44DA8AB6A004DEC9553760BD5896801
         * path :
         * name : 陈伟强
         */

        private String guid;
        private String path;
        private String name;

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
