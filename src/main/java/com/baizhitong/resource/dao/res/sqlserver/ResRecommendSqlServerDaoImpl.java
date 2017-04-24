package com.baizhitong.resource.dao.res.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.res.ResRecommendDao;
import com.baizhitong.resource.model.res.ResRecommend;
import com.baizhitong.utils.RowMapperUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 资源_推荐数据SqlServer接口实现
 * 
 * @author creator shancy 2015/12/16
 * @author updater
 */
@Service("resRecommendSqlServerDao")
public class ResRecommendSqlServerDaoImpl extends BaseSqlServerDao<ResRecommend> implements ResRecommendDao {
    /**
     * 获取首页推荐视频资源集合
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param isHotSort 是否最热降序
     * @param isNewSort 是否最新降序
     * @return 首页推荐视频资源集合信息
     * @exception 异常
     */
    @Override
    public List<Map<String, Object>> selectMediaResRecommendList(String sectionCode, String subjectCode,
                    Boolean isHotSort, Boolean isNewSort) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT TOP 12 rm.id, rm.resName ");
        sql.append(" FROM res_recommend rr ");
        sql.append(" INNER JOIN res_media rm ON rm.resTypeL1 = rr.resTypeL1 AND rm.id = rr.resId ");
        sql.append(" LEFT JOIN rel_res_section rrse ON rrse.resTypeL1 = rm.resTypeL1 AND rrse.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_section scse ON scse.code = rrse.sectionCode ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rm.resTypeL1 AND rrs.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" WHERE rr.flagDelete = 0 AND rm.shareLevel = 60 AND　rm.flagDelete = 0 AND rm.flagAllowBrowse = 1 AND rm.shareCheckStatus = 20 AND rm.resStatus = 20 ");

        // 判断学段编码是否为空
        if (!StringUtils.isEmpty(sectionCode)) {
            sql.append(" AND rrse.sectionCode = :sectionCode ");
        }
        // 判断学科编码是否为空
        if (!StringUtils.isEmpty(subjectCode)) {
            sql.append(" AND rrs.subjectCode = :subjectCode ");
        }

        // 排序方式:最热
        if (isHotSort != null && isHotSort) {
            sql.append(" ORDER BY rm.browseCount DESC, rm.id ");
        }
        // 排序方式:最新
        if (isNewSort != null && isNewSort) {
            sql.append(" ORDER BY rm.makeTime DESC, rm.id ");
        }

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sectionCode", sectionCode);
        map.put("subjectCode", subjectCode);

        // 执行SQL
        return super.findBySql(sql.toString(), map);
    }

    /**
     * 获取首页推荐文档资源集合
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param isHotSort 是否最热降序
     * @param isNewSort 是否最新降序
     * @return 首页推荐文档资源集合信息
     * @exception 异常
     */
    @Override
    public List<Map<String, Object>> selectDocResRecommendList(String sectionCode, String subjectCode,
                    Boolean isHotSort, Boolean isNewSort) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT TOP 12 rd.id, rd.resName ");
        sql.append(" FROM res_recommend rr ");
        sql.append(" INNER JOIN res_doc rd ON rd.resTypeL1 = rr.resTypeL1 AND rd.id = rr.resId ");
        sql.append(" LEFT JOIN rel_res_section rrse ON rrse.resTypeL1 = rd.resTypeL1 AND rrse.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_section scse ON scse.code = rrse.sectionCode ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rd.resTypeL1 AND rrs.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" WHERE rr.flagDelete = 0 AND rd.shareLevel = 60 AND　rd.flagDelete = 0 AND rd.flagAllowBrowse = 1 AND rd.shareCheckStatus = 20 AND rd.resStatus = 20 ");

        // 判断学段编码是否为空
        if (!StringUtils.isEmpty(sectionCode)) {
            sql.append(" AND rrse.sectionCode = :sectionCode ");
        }
        // 判断学科编码是否为空
        if (!StringUtils.isEmpty(subjectCode)) {
            sql.append(" AND rrs.subjectCode = :subjectCode ");
        }

        // 排序方式:最热
        if (isHotSort != null && isHotSort) {
            sql.append(" ORDER BY rd.browseCount DESC, rd.id ");
        }
        // 排序方式:最新
        if (isNewSort != null && isNewSort) {
            sql.append(" ORDER BY rd.makeTime DESC, rd.id ");
        }

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sectionCode", sectionCode);
        map.put("subjectCode", subjectCode);

        // 执行SQL
        return super.findBySql(sql.toString(), map);
    }

    /**
     * 获取首页更多推荐视频资源集合
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param mediaName 视频名称
     * @param pageNo 当前页数
     * @param pageSize 每页条数
     * @param isHotSort 是否最热降序
     * @param isNewSort 是否最新降序
     * @return 首页更多推荐视频资源集合
     * @exception 异常
     */
    @Override
    @SuppressWarnings("unchecked")
    public Page<Map<String, Object>> selectMediaResMoreRecommendPage(String sectionCode, String subjectCode,
                    String mediaName, Integer pageNo, Integer pageSize, Boolean isHotSort, Boolean isNewSort)
                                    throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT rm.id, rm.resName, rm.mediaDuration, rm.thumbnailPath, rm.trialTimeRate, rm.browseCount, rm.downloadCount, rm.goodCount, ");
        sql.append(" rm.commentCount, rm.userName, rm.makeTime, scse.name AS sectionName, scs.name AS subjectName, scg.name AS gradeName, sctv.name AS textbookVerName ");
        sql.append(" FROM res_recommend rr ");
        sql.append(" INNER JOIN res_media rm ON rm.resTypeL1 = rr.resTypeL1 AND rm.id = rr.resId ");
        sql.append(" LEFT JOIN rel_res_section rrse ON rrse.resTypeL1 = rm.resTypeL1 AND rrse.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_section scse ON scse.code = rrse.sectionCode ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rm.resTypeL1 AND rrs.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rm.resTypeL1 AND rrg.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" LEFT JOIN rel_res_tbv rrt ON rrt.resTypeL1 = rm.resTypeL1 AND rrt.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rrt.tbvCode ");
        sql.append(" WHERE rr.flagDelete = 0 AND rm.shareLevel = 60 AND　rm.flagDelete = 0 AND rm.flagAllowBrowse = 1 AND rm.shareCheckStatus = 20 AND rm.resStatus = 20 ");

        // 判断学段编码是否为空
        if (!StringUtils.isEmpty(sectionCode)) {
            sql.append(" AND rrse.sectionCode = :sectionCode ");
        }
        // 判断学科编码是否为空
        if (!StringUtils.isEmpty(subjectCode)) {
            sql.append(" AND rrs.subjectCode = :subjectCode ");
        }
        // 判断视频名称是否为空
        if (!StringUtils.isEmpty(mediaName)) {
            sql.append(" AND rm.resName LIKE '%" + mediaName + "%' ");
        }

        // 排序方式:最热
        if (isHotSort != null && isHotSort) {
            sql.append(" ORDER BY rm.browseCount DESC, rm.id ");
        }
        // 排序方式:最新
        if (isNewSort != null && isNewSort) {
            sql.append(" ORDER BY rm.makeTime DESC, rm.id ");
        }

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sectionCode", sectionCode);
        map.put("subjectCode", subjectCode);
        map.put("resName", mediaName);

        // 执行SQL
        return super.queryDistinctPage(sql.toString(), map, pageNo, pageSize);
    }

    /**
     * 获取首页更多推荐文档资源集合
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param docName 文档名称
     * @param pageNo 当前页数
     * @param pageSize 每页条数
     * @param isHotSort 是否最热降序
     * @param isNewSort 是否最新降序
     * @return 首页更多推荐文档资源集合
     * @exception 异常
     */
    @Override
    @SuppressWarnings("unchecked")
    public Page<Map<String, Object>> selectDocResMoreRecommendPage(String sectionCode, String subjectCode,
                    String docName, Integer pageNo, Integer pageSize, Boolean isHotSort, Boolean isNewSort)
                                    throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT rd.id, rd.resName, rd.suffix, rd.coverPath, rd.flagDynamicPPT, rd.browseCount, rd.downloadCount, rd.referCount, ");
        sql.append(" rd.favoriteCount, rd.goodCount, rd.badCount, rd.commentCount, rd.resDesc, rd.userName, rd.makeTime, ");
        sql.append(" scse.name AS sectionName, scs.name AS subjectName, scg.name AS gradeName, sctv.name AS textbookVerName ");
        sql.append(" FROM res_recommend rr ");
        sql.append(" INNER JOIN res_doc rd ON rd.resTypeL1 = rr.resTypeL1 AND rd.id = rr.resId ");
        sql.append(" LEFT JOIN rel_res_section rrse ON rrse.resTypeL1 = rd.resTypeL1 AND rrse.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_section scse ON scse.code = rrse.sectionCode ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rd.resTypeL1 AND rrs.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rd.resTypeL1 AND rrg.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" LEFT JOIN rel_res_tbv rrt ON rrt.resTypeL1 = rd.resTypeL1 AND rrt.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rrt.tbvCode ");
        sql.append(" WHERE rr.flagDelete = 0 AND rd.shareLevel = 60 AND　rd.flagDelete = 0 AND rd.flagAllowBrowse = 1 AND rd.shareCheckStatus = 20 AND rd.resStatus = 20 ");

        // 判断学段编码是否为空
        if (!StringUtils.isEmpty(sectionCode)) {
            sql.append(" AND rrse.sectionCode = :sectionCode ");
        }
        // 判断学科编码是否为空
        if (!StringUtils.isEmpty(subjectCode)) {
            sql.append(" AND rrs.subjectCode = :subjectCode ");
        }
        // 判断文档名称是否为空
        if (!StringUtils.isEmpty(docName)) {
            sql.append(" AND rd.resName LIKE '%" + docName + "%' ");
        }

        // 排序方式:最热
        if (isHotSort != null && isHotSort) {
            sql.append(" ORDER BY rd.browseCount DESC, rd.id ");
        }
        // 排序方式:最新
        if (isNewSort != null && isNewSort) {
            sql.append(" ORDER BY rd.makeTime DESC, rd.id ");
        }

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sectionCode", sectionCode);
        map.put("subjectCode", subjectCode);
        map.put("resName", docName);

        // 执行SQL
        return super.queryDistinctPage(sql.toString(), map, pageNo, pageSize);
    }

    /**
     * 获取被推荐的文档资源分页信息
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<Map<String, Object>> getRecommendDocInfo(Map<String, Object> param) throws Exception {
        // 获取查询参数
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String recommendStartTime = MapUtils.getString(param, "recommendStartTime");
        String recommendEndTime = MapUtils.getString(param, "recommendEndTime");
        String resName = MapUtils.getString(param, "resName");
        Integer pageNumber = MapUtils.getInteger(param, "pageNumber");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");

        // 拼接SQL语句
        StringBuilder sqlStr = new StringBuilder();
        sqlStr.append(" select rd.resName,rd.userName,rd.makeTime,scg.name as gradeName,scs.name as sectionName, ");
        sqlStr.append(" scs2.name as subjectName,rd.creator,rr.recommendTime,rd.resDesc, ");
        sqlStr.append(" rd.browseCount,rd.downloadCount,rd.goodCount,rd.commentCount,rd.downloadPoints,rr.id,rr.resId ");
        sqlStr.append(" from res_recommend rr ");
        sqlStr.append(" LEFT JOIN res_doc rd on rr.resId=rd.id ");
        sqlStr.append(" LEFT JOIN rel_res_grade rrg on rrg.resId=rd.id and rrg.resTypeL1=rd.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_grade scg on rrg.gradeCode=scg.code ");
        sqlStr.append(" LEFT JOIN rel_res_section rrs on rrs.resId=rd.id and rrs.resTypeL1=rd.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_section scs on rrs.sectionCode=scs.code ");
        sqlStr.append(" LEFT JOIN rel_res_subject rrs2 on rrs2.resId=rd.id and rrs2.resTypeL1=rd.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_subject scs2 on rrs2.subjectCode=scs2.code ");
        sqlStr.append(" where rr.flagDelete=0 and rd.flagDelete=0 and rr.resTypeL1=20 and rd.resTypeL1=20 ");
        sqlStr.append(" and rd.shareCheckStatus=20 ");// 已审核的资源
        // 查询条件
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(sectionCode)) {
            sqlStr.append(" and rrs.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (!StringUtils.isEmpty(gradeCode)) {
            sqlStr.append(" and rrg.gradeCode=:gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (!StringUtils.isEmpty(subjectCode)) {
            sqlStr.append(" and rrs2.subjectCode=:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (!StringUtils.isEmpty(resName)) {
            sqlStr.append(" and rd.resName like '%" + resName + "%' ");
        }
        if (!StringUtils.isEmpty(recommendStartTime)) {
            sqlStr.append(" and rr.recommendTime >=:recommendStartTime ");
            sqlParam.put("recommendStartTime", recommendStartTime);
        }
        if (!StringUtils.isEmpty(recommendEndTime)) {
            sqlStr.append(" and rr.recommendTime <=:recommendEndTime ");
            sqlParam.put("recommendEndTime", recommendEndTime);
        }
        sqlStr.append(" order by rr.recommendTime desc ");
        // 执行查询
        return super.queryDistinctPage(sqlStr.toString(), new RowMapperUtils(), sqlParam, pageNumber, pageSize);
    }

    /**
     * 取消资源推荐
     */
    @Override
    public int deleteRecommendRes(String resId, Integer resTypeL1) throws Exception {
        String sql = "update res_recommend set flagDelete=1 where resId=:resId and resTypeL1=:resTypeL1";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("resId", resId);
        sqlParam.put("resTypeL1", resTypeL1);
        return super.update(sql, sqlParam);
    }

    /**
     * 得到所有的被推荐的文档资源id的Map集合
     */
    @Override
    public List<Map<String, Object>> getResIdMapList(Integer resTypeL1) throws Exception {
        String sql = "select resId from res_recommend where flagDelete=0 and resTypeL1=:resTypeL1";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("resTypeL1", resTypeL1);
        return super.findBySql(sql, sqlParam);
    }

    /**
     * 根据资源ID和资源一级分类查询资源推荐信息
     */
    @Override
    public ResRecommend getInfoByResIdAndResTypeL1(String resId, Integer resTypeL1) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("resId", resId);
        qr.andEqual("resTypeL1", resTypeL1);
        return super.findUnique(qr);
    }

    /**
     * 添加资源推荐
     */
    @Override
    public boolean addResRecommend(ResRecommend entity) throws Exception {
        return super.saveOne(entity);
    }

    /**
     * 查询资源推荐信息 ()<br>
     * 
     * @param param
     * @return
     * @throws Exception
     */
    public Page getRecommendResInfo(Map<String, Object> param) throws Exception {
        // 获取查询参数
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String recommendStartTime = MapUtils.getString(param, "recommendStartTime");
        String recommendEndTime = MapUtils.getString(param, "recommendEndTime");
        String resName = MapUtils.getString(param, "resName");
        Integer pageNumber = MapUtils.getInteger(param, "pageNumber");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        Integer resTypeL1 = MapUtils.getInteger(param, "resTypeL1");
        // 拼接SQL语句
        StringBuilder sqlStr = new StringBuilder();
        sqlStr.append(" select b.* from (");
        sqlStr.append(" ( ");
        sqlStr.append(" select rm.resName,rm.makerName,rm.makeTime,scg.name as gradeName,scs.name as sectionName, ");
        sqlStr.append(" scs2.name as subjectName,rm.creator,rr.recommendTime,rm.resDesc, ");
        sqlStr.append(" rm.browseCount,rm.downloadCount,rm.goodCount,rm.commentCount,rm.downloadPoints,rr.id,rr.resId,rm.resTypeL1,rrs.sectionCode,rrg.gradeCode,rrs2.subjectCode ");
        sqlStr.append(" from res_recommend rr ");
        sqlStr.append(" LEFT JOIN res_media rm on rr.resId=rm.id ");
        sqlStr.append(" LEFT JOIN rel_res_grade rrg on rrg.resId=rm.id and rrg.resTypeL1=rm.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_grade scg on rrg.gradeCode=scg.code ");
        sqlStr.append(" LEFT JOIN rel_res_section rrs on rrs.resId=rm.id and rrs.resTypeL1=rm.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_section scs on rrs.sectionCode=scs.code ");
        sqlStr.append(" LEFT JOIN rel_res_subject rrs2 on rrs2.resId=rm.id and rrs2.resTypeL1=rm.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_subject scs2 on rrs2.subjectCode=scs2.code ");
        sqlStr.append(" where rr.flagDelete=0 and rm.flagDelete=0 and rr.resTypeL1=10 and rm.resTypeL1=10 ");
        sqlStr.append(" and rm.shareCheckStatus=20 ");// 已审核的资源
        sqlStr.append(" ) ");
        sqlStr.append(" Union all ");
        sqlStr.append(" ( ");
        sqlStr.append("  select rd.resName,rd.makerName,rd.makeTime,scg.name as gradeName,scs.name as sectionName, ");
        sqlStr.append(" scs2.name as subjectName,rd.creator,rr.recommendTime,rd.resDesc, ");
        sqlStr.append(" rd.browseCount,rd.downloadCount,rd.goodCount,rd.commentCount,rd.downloadPoints,rr.id,rr.resId ,rd.resTypeL1,rrs.sectionCode,rrg.gradeCode,rrs2.subjectCode ");
        sqlStr.append(" from res_recommend rr ");
        sqlStr.append(" LEFT JOIN res_doc rd on rr.resId=rd.id ");
        sqlStr.append(" LEFT JOIN rel_res_grade rrg on rrg.resId=rd.id and rrg.resTypeL1=rd.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_grade scg on rrg.gradeCode=scg.code ");
        sqlStr.append(" LEFT JOIN rel_res_section rrs on rrs.resId=rd.id and rrs.resTypeL1=rd.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_section scs on rrs.sectionCode=scs.code ");
        sqlStr.append(" LEFT JOIN rel_res_subject rrs2 on rrs2.resId=rd.id and rrs2.resTypeL1=rd.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_subject scs2 on rrs2.subjectCode=scs2.code ");
        sqlStr.append(" where rr.flagDelete=0 and rd.flagDelete=0 and rr.resTypeL1=20 and rd.resTypeL1=20 ");
        sqlStr.append(" and rd.shareCheckStatus=20 ");// 已审核的资源
        sqlStr.append(" ) ");
        sqlStr.append(" ) b");
        sqlStr.append(" where 1=1  ");
        // 查询条件
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(sectionCode)) {
            sqlStr.append(" and b.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (!StringUtils.isEmpty(gradeCode)) {
            sqlStr.append(" and b.gradeCode=:gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (!StringUtils.isEmpty(subjectCode)) {
            sqlStr.append(" and b.subjectCode=:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (!StringUtils.isEmpty(resName)) {
            sqlStr.append(" and b.resName like '%" + resName + "%' ");
        }
        if (!StringUtils.isEmpty(recommendStartTime)) {
            sqlStr.append(" and b.recommendTime >=:recommendStartTime ");
            sqlParam.put("recommendStartTime", recommendStartTime);
        }
        if (!StringUtils.isEmpty(recommendEndTime)) {
            sqlStr.append(" and b.recommendTime <=:recommendEndTime ");
            sqlParam.put("recommendEndTime", recommendEndTime);
        }
        if (resTypeL1 != null) {
            sqlStr.append(" and b.resTypeL1 =:resTypeL1 ");
            sqlParam.put("resTypeL1", resTypeL1);
        }
        sqlStr.append(" order by b.recommendTime desc ");
        // 执行查询
        return super.queryDistinctPage(sqlStr.toString(), new RowMapperUtils(), sqlParam, pageNumber, pageSize);

    }

    /**
     * 获取被推荐的音视频资源分页信息
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<Map<String, Object>> getRecommendMediaInfo(Map<String, Object> param) throws Exception {
        // 获取查询参数
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String recommendStartTime = MapUtils.getString(param, "recommendStartTime");
        String recommendEndTime = MapUtils.getString(param, "recommendEndTime");
        String resName = MapUtils.getString(param, "resName");
        Integer pageNumber = MapUtils.getInteger(param, "pageNumber");
        Integer pageSize = MapUtils.getInteger(param, "pageSize");

        // 拼接SQL语句
        StringBuilder sqlStr = new StringBuilder();
        sqlStr.append(" select rm.resName,scg.name as gradeName,rm.userName,rm.makeTime,scs.name as sectionName, ");
        sqlStr.append(" scs2.name as subjectName,rm.creator,rr.recommendTime,rm.resDesc, ");
        sqlStr.append(" rm.browseCount,rm.downloadCount,rm.goodCount,rm.commentCount,rm.downloadPoints,rr.id,rr.resId ");
        sqlStr.append(" from res_recommend rr ");
        sqlStr.append(" LEFT JOIN res_media rm on rr.resId=rm.id ");
        sqlStr.append(" LEFT JOIN rel_res_grade rrg on rrg.resId=rm.id and rrg.resTypeL1=rm.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_grade scg on rrg.gradeCode=scg.code ");
        sqlStr.append(" LEFT JOIN rel_res_section rrs on rrs.resId=rm.id and rrs.resTypeL1=rm.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_section scs on rrs.sectionCode=scs.code ");
        sqlStr.append(" LEFT JOIN rel_res_subject rrs2 on rrs2.resId=rm.id and rrs2.resTypeL1=rm.resTypeL1 ");
        sqlStr.append(" LEFT JOIN share_code_subject scs2 on rrs2.subjectCode=scs2.code ");
        sqlStr.append(" where rr.flagDelete=0 and rm.flagDelete=0 and rr.resTypeL1=10 and rm.resTypeL1=10 ");
        sqlStr.append(" and rm.shareCheckStatus=20 ");// 已审核的资源
        // 查询条件
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(sectionCode)) {
            sqlStr.append(" and rrs.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (!StringUtils.isEmpty(gradeCode)) {
            sqlStr.append(" and rrg.gradeCode=:gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (!StringUtils.isEmpty(subjectCode)) {
            sqlStr.append(" and rrs2.subjectCode=:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (!StringUtils.isEmpty(resName)) {
            sqlStr.append(" and rm.resName like '%" + resName + "%' ");
        }
        if (!StringUtils.isEmpty(recommendStartTime)) {
            sqlStr.append(" and rr.recommendTime >=:recommendStartTime ");
            sqlParam.put("recommendStartTime", recommendStartTime);
        }
        if (!StringUtils.isEmpty(recommendEndTime)) {
            sqlStr.append(" and rr.recommendTime <=:recommendEndTime ");
            sqlParam.put("recommendEndTime", recommendEndTime);
        }
        sqlStr.append(" order by rr.recommendTime desc ");
        // 执行查询
        return super.queryDistinctPage(sqlStr.toString(), new RowMapperUtils(), sqlParam, pageNumber, pageSize);
    }
}