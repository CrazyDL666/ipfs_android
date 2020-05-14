package io.ipfs.videoshare.bean;

import java.util.List;

public class UserBean {
    /**
     * avatar : /ipfs/QmQQ9rM3rgyHFy8L9YsRBVYCjpYkbBMCZoZXtN5PphHqnA
     * description : 瀹樻柟棰戦亾
     * id : QmXidpbD1osmHXWN4gJc3NHry3kzTnicnp9Utrpxk6s4Du
     * type : [{"name":"plot","title":"鍓ф儏"},{"name":"love","title":"鐖辨儏"},{"name":"history","title":"鍘嗗彶"},{"name":"warfare","title":"鎴樹簤"},{"name":"thriller","title":"鎯婃倸"},{"name":"crime","title":"鐘姜"},{"name":"suspense","title":"鎮枒"},{"name":"comedy","title":"鍠滃墽"},{"name":"action","title":"鍔ㄤ綔"},{"name":"fantasy","title":"濂囧够"},{"name":"adventure","title":"鍐掗櫓"},{"name":"biography","title":"浼犺"},{"name":"ancient","title":"鍙よ"},{"name":"animation","title":"鍔ㄧ敾"},{"name":"burlesque","title":"姝岃垶"},{"name":"sciencefiction","title":"绉戝够"},{"name":"family","title":"瀹跺涵"},{"name":"disaster","title":"鐏鹃毦"},{"name":"short","title":"鐭墖"}]
     * username : imba[鐢靛奖]
     */

    private String avatar;
    private String description;
    private String id;
    private String username;
    private List<TypeBean> type;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<TypeBean> getType() {
        return type;
    }

    public void setType(List<TypeBean> type) {
        this.type = type;
    }

    public static class TypeBean {
        /**
         * name : plot
         * title : 鍓ф儏
         */

        private String name;
        private String title;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
