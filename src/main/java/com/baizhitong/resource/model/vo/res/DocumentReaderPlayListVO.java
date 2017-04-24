package com.baizhitong.resource.model.vo.res;

/**
 * 文档播放器阅读列表后台对应对象
 * 
 * @author zhangll
 *
 */
public class DocumentReaderPlayListVO {
    /**
     * 总页数
     */
    public int                        totalPages;
    /**
     * 每页帧数
     */
    public int                        pageFp = DEFAULT_PAGE_FP;
    /**
     * 
     */
    public int                        totalRec;
    /**
     * 当前页数
     */
    public int                        pageNo = 1;
    /**
     * datas数据
     */
    public DocumentReaderPlayDataVO[] datas  = {};

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageFp() {
        return pageFp;
    }

    public void setPageFp(int pageFp) {
        this.pageFp = pageFp;
    }

    public int getTotalRec() {
        return totalRec;
    }

    public void setTotalRec(int totalRec) {
        this.totalRec = totalRec;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public DocumentReaderPlayDataVO[] getDatas() {
        return datas;
    }

    public void setDatas(DocumentReaderPlayDataVO[] datas) {
        this.datas = datas;
    }

    private static final int DEFAULT_PAGE_FP = 1;

    public static int getDefaultPageFp() {
        return DEFAULT_PAGE_FP;
    }
}
