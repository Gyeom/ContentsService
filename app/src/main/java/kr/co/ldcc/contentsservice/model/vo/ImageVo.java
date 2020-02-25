package kr.co.ldcc.contentsservice.model.vo;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageVo implements Parcelable {
    private String collection;
    private String thumbnail_url;
    private String image_url;
    private int width;
    private int height;
    private String display_sitename;
    private String doc_url;
    private String datetime;

    public ImageVo(String collection, String thumbnail_url, String image_url, int width, int height, String display_sitename, String doc_url, String datetime) {
        this.collection = collection;
        this.thumbnail_url = thumbnail_url;
        this.image_url = image_url;
        this.width = width;
        this.height = height;
        this.display_sitename = display_sitename;
        this.doc_url = doc_url;
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "ImageVo{" +
                "collection='" + collection + '\'' +
                ", thumbnail_url='" + thumbnail_url + '\'' +
                ", image_url='" + image_url + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", display_sitename='" + display_sitename + '\'' +
                ", doc_url='" + doc_url + '\'' +
                ", datetime='" + datetime + '\'' +
                '}';
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getDisplay_sitename() {
        return display_sitename;
    }

    public void setDisplay_sitename(String display_sitename) {
        this.display_sitename = display_sitename;
    }

    public String getDoc_url() {
        return doc_url;
    }

    public void setDoc_url(String doc_url) {
        this.doc_url = doc_url;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.collection);
        dest.writeString(this.thumbnail_url);
        dest.writeString(this.image_url);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.display_sitename);
        dest.writeString(this.doc_url);
        dest.writeString(this.datetime);
    }

    protected ImageVo(Parcel in) {
        this.collection = in.readString();
        this.thumbnail_url = in.readString();
        this.image_url = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.display_sitename = in.readString();
        this.doc_url = in.readString();
        this.datetime = in.readString();
    }

    public static final Creator<ImageVo> CREATOR = new Creator<ImageVo>() {
        @Override
        public ImageVo createFromParcel(Parcel source) {
            return new ImageVo(source);
        }

        @Override
        public ImageVo[] newArray(int size) {
            return new ImageVo[size];
        }
    };
}
