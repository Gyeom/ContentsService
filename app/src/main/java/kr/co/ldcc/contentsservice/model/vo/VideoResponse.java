package kr.co.ldcc.contentsservice.model.vo;

import java.util.ArrayList;

public class VideoResponse {
    private VideoMeta meta;
    private ArrayList<VideoVo> documents;

    public VideoResponse(VideoMeta meta, ArrayList<VideoVo> documents) {
        this.meta = meta;
        this.documents = documents;
    }

    public VideoMeta getMeta() {
        return meta;
    }

    public void setMeta(VideoMeta meta) {
        this.meta = meta;
    }

    public ArrayList<VideoVo> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<VideoVo> documents) {
        this.documents = documents;
    }
}
