package kr.co.ldcc.contentsservice.model.vo;

import java.util.ArrayList;

public class ImageResponse {

    private ImageMeta meta;
    private ArrayList<ImageVo> documents;

    public ImageResponse(ImageMeta meta, ArrayList<ImageVo> documents) {
        this.meta = meta;
        this.documents = documents;
    }

    public ImageMeta getMeta() {
        return meta;
    }

    public void setMeta(ImageMeta meta) {
        this.meta = meta;
    }

    public ArrayList<ImageVo> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<ImageVo> documents) {
        this.documents = documents;
    }
}
