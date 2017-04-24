package com.baizhitong.resource.dao.demo.mysql;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.baizhitong.common.dao.jdbc.PageCompositeDaoSupport;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.dao.demo.DemoDao;
import com.baizhitong.resource.model.demo.Demo;
import com.baizhitong.resource.model.demo.Demo.Status;
import com.baizhitong.resource.model.vo.temp.StudentTempVo;
import com.baizhitong.resource.model.vo.temp.TeacherTempVo;
import com.baizhitong.resource.model.vo.temp.adminClassSubjectTempVo;
import com.baizhitong.resource.model.vo.temp.adminClassVo;

@Service("demoMySqlDao")
public class CompositeDemoMySqlDaoImpl extends PageCompositeDaoSupport<Demo> implements DemoDao {

    private Logger logger = LoggerFactory.getLogger(CompositeDemoMySqlDaoImpl.class);

    @Resource(name = "dataSource_mooc")
    public void setDataSource(DataSource dataSource) {
        super.setDataSourceReadOnly(dataSource);
        super.setDataSourceWrite(dataSource);
    }

    @Override
    public List<Demo> select() {
        try {
            Demo demo = new Demo();
            demo.setId2(12);
            demo.setCode("0007");
            demo.setName("第七");
            demo.setLong0(1123412341234l);
            demo.setInteger0(81241);
            demo.setDouble0(123412.12d);
            demo.setFloat0(1234.4f);
            demo.setStatus(Status.enabled);
            demo.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

            List<Demo> demoList = new ArrayList<Demo>();
            demoList.add(demo);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("code", "0008");
            params.put("name", "第ba");
            params.put("id2", 123);
            params.put("updateTime", new Timestamp(new java.util.Date().getTime()));

            String tableName = "demo";
            String pkName = "id";
            Integer pkValue = 3;

            QueryRule queryRule = QueryRule.getInstance();
            queryRule.andEqual("id", pkValue);

            Integer pageNo = 2;
            Integer pageSize = 5;

            String sql = "select * from demo where code = ?";

            Map<String, Object> queryParams = new HashMap<String, Object>();
            queryParams.put("code", "0003");

            Map<String, Object> pkParams = new HashMap<String, Object>();
            pkParams.put("id2", "0003");
            pkParams.put("id", 791);

            // 分页查询相关测试
            queryRule = QueryRule.getInstance();
            queryRule.addAscOrder("id");
            queryRule.andEqual("name", "第ba");

            super.find(queryRule, pageNo, pageSize);
            Map map = new HashMap();
            map.put("name", "第ba");
            super.queryDistinctPage("select * from demo where name = :name order by id desc", this.op.rowMapper, map, 1,
                            14);

            super.doInsert(demo);
            super.doInsert(params);
            super.doInsert(tableName, params);
            super.doInsertRuturnKey(params);

            super.saveAll(Arrays.asList(demo));
            super.saveAndReturnId(demo);
            super.saveOne(demo);

            super.update(demo);
            String updateSql = "update demo set code = :code,name=:name,updateTime=:updateTime where id = 15";
            super.update(updateSql, params);
            updateSql = "update demo set code = 'ccccccccccccc' where id = ?";
            super.update(updateSql, pkValue);

            super.delete(demo);
            super.deleteAll(demoList);

            super.deleteByPK(pkParams);
            super.doDelete(pkParams);
            super.doDelete(tableName, pkName, pkValue);
            // super.doLoad(pkValue, rowMapper)
            // super.doLoad(tableName, pkName, pkValue, rowMapper)
            super.doUpdate(pkParams, params);
            super.doUpdate(pkName, pkValue, params);
            super.doUpdate(tableName, pkName, pkValue, params);
            super.exists(pkParams);
            super.find(queryRule);
            super.find(queryRule, pageNo, pageSize);

            List paramsList = new ArrayList();
            paramsList.add(pkValue);

            super.findBySql(sql, paramsList);
            sql = "select * from demo where code = :code ";
            super.findBySql(sql, queryParams);
            sql = "select * from demo where code = ?";
            super.findBySql(sql, pkValue);
            // super.findUnique(queryParams);
            // super.findUnique(queryRule);
            // super.findUnique(pkName, pkValue);
            super.findUniqueBySql(sql, paramsList);
            sql = "select * from demo where code = :code ";
            super.findUniqueBySql(sql, queryParams);
            sql = "select * from demo where code = ?";
            super.findUniqueBySql(sql, pkValue);
            super.findUniqueBySql(Demo.class, sql, paramsList);
            super.findUniqueBySql(Demo.class, sql, pkValue);

            demo.setId(10l);
            super.forceUpdate(demo);
            super.forceUpdate(pkParams, params);

            super.get(pkParams);
            super.getAll();
            super.getCount(queryRule);
            // super.getMax(propertyName)
            // super.getMaxId(table, column);
            // super.getOp()
            // super.getPKColumn()
            // super.getShards()

            sql = "select * from demo where code = :code ";
            super.queryCount(sql, queryParams);
            // super.queryDistinctPage(sql, rowMapper, args, pageNo, pageSize)
            // super.queryForObject(sql, mapper, args)

        } catch (Exception e) {
            logger.error("", e);
        }

        List<Demo> list = super.getAll();
        return list;
    }

    @Override
    public void insertStudent(StudentTempVo student) {
        // TODO Auto-generated method stub

    }

    @Override
    public void insertTeacher(TeacherTempVo teacher) {
        // TODO Auto-generated method stub

    }

    @Override
    public void insertAdminClass(adminClassVo adminClass) {
        // TODO Auto-generated method stub

    }

    @Override
    public void insertTeacherSubjectClass(adminClassSubjectTempVo adminClassSubject) {
        // TODO Auto-generated method stub

    }

    @Override
    public int updateOrgUpdateBatch(String orgGuid, Integer batch) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Map<String, Object> getOrgUpdateBatch(String orgGuid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void insertInite(String orgGuid, Integer batch) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAll(String orgCode) {
        // TODO Auto-generated method stub

    }

    @Override
    public void insertTest() {
        // TODO Auto-generated method stub

    }
}
