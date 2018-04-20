package test.lxl.com.demo;

import java.io.Serializable;

/**
 * Created by Luxulong on 2018/4/18.
 */

public class PictureSelectBean implements Serializable {

    private String path;
    private String name;
    private int index;
    private int selectIndex;
    private String url;
    private boolean isSelected;
    private boolean previewSelected;

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    public boolean isPreviewSelected() {
        return previewSelected;
    }

    public void setPreviewSelected(boolean previewSelected) {
        this.previewSelected = previewSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
