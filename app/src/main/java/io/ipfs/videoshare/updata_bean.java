package io.ipfs.videoshare;

import java.util.List;

/**
 * Created by Administrator on 2020/3/25.
 */

public class updata_bean {

    /**
     * data : [{"apk_file":"apk/videoshare_1.0.1_8.apk","bulid":"8","datetime":1584888321,"log":"更新内容2","title":"测试版1.02上线","version":"1.0.1"},{"apk_file":"apk/videoshare_0.0.1_1.apk","bulid":"1","datetime":1585145044,"log":"初始化","title":"初始化","version":"0.0.1"}]
     * title : VideoShare
     */

    private String title;
    private List<DataBean> data;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * apk_file : apk/videoshare_1.0.1_8.apk
         * bulid : 8
         * datetime : 1584888321
         * log : 更新内容2
         * title : 测试版1.02上线
         * version : 1.0.1
         */

        private String apk_file;
        private String build;
        private int datetime;
        private String log;
        private String title;
        private String version;

        public String getApk_file() {
            return apk_file;
        }

        public void setApk_file(String apk_file) {
            this.apk_file = apk_file;
        }

        public String getBuild() {
            return build;
        }

        public void setBuild(String build) {
            this.build = build;
        }

        public int getDatetime() {
            return datetime;
        }

        public void setDatetime(int datetime) {
            this.datetime = datetime;
        }

        public String getLog() {
            return log;
        }

        public void setLog(String log) {
            this.log = log;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
