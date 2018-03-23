package cn.cfanr.izhihudaily.model;

import java.util.List;

/**
 * @author xifan
 *         date: 2016/8/16
 *         desc:
 */
public class Launch {

    private List<Creative> creatives;

    public List<Creative> getCreatives() {
        return creatives;
    }

    public void setCreatives(List<Creative> creatives) {
        this.creatives = creatives;
    }

    public static class Creative {
        /**
         * url : https://pic4.zhimg.com/v2-e78323f847d52e2e3c7db6107b09f533.jpg
         * start_time : 1493719313
         * impression_tracks : ["https://sugar.zhihu.com/track?vs=1&ai=3957&ut=&cg=2&ts=1493719313.59&si=23f7fb2bbf134db0b56cab2ca4f8b3af&lu=0&hn=ad-engine.ad-engine.f3e2588d&at=impression&pf=PC&az=11&sg=8d3c020053473c99170e9ea4442fed6a"]
         * type : 0
         * id : 3957
         */

        private String url;
        private int start_time;
        private int type;
        private String id;
        private List<String> impression_tracks;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getStart_time() {
            return start_time;
        }

        public void setStart_time(int start_time) {
            this.start_time = start_time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getImpression_tracks() {
            return impression_tracks;
        }

        public void setImpression_tracks(List<String> impression_tracks) {
            this.impression_tracks = impression_tracks;
        }
    }
}
