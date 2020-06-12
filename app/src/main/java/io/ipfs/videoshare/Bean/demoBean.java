package io.ipfs.videoshare.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.ipfs.videoshare.bean.VideoBean;

public class demoBean {
    private List<FilesBean> demoVideos;

    public List<FilesBean> getDemoVideos() {
        return demoVideos;
    }

    public void setDemoVideos(List<FilesBean> demoVideos) {
        this.demoVideos = demoVideos;
    }

    public static class FilesBean {

        private String title;
        private String cover;
        private int duration;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
