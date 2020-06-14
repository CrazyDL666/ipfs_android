package io.ipfs.videoshare.bean;

import java.util.List;

public class VideoListBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cover : /ipfs/QmYXtE3K9BPJoyXsXZcC2vyi5yt3XkkfEckQg3S7wuZTa1
         * title : 鍔犲瘑璐у竵
         * url : /ipfs/QmcDR7L3URnWMY475zWYMtCGKgsvyom4rGrsdu18rgdmtU
         */

        private String cover;
        private String title;
        private String url;

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
