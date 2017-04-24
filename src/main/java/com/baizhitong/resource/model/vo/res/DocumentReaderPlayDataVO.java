package com.baizhitong.resource.model.vo.res;

/**
 * 文档播放器阅读列表里的数据对应对象
 * 
 * @author zhangll
 *
 */
public class DocumentReaderPlayDataVO {

    public String getPageUrl;

    public int    pageId;

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public String getGetPageUrl() {
        return getPageUrl;
    }

    public void setGetPageUrl(String getPageUrl) {
        this.getPageUrl = getPageUrl;
    }

}
