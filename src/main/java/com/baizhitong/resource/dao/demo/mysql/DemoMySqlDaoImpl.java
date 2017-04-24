package com.baizhitong.resource.dao.demo.mysql;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseMySqlDao;
import com.baizhitong.resource.dao.demo.DemoDao;
import com.baizhitong.resource.model.demo.Demo;
import com.baizhitong.resource.model.demo.Demo.Status;
import com.baizhitong.resource.model.vo.temp.StudentTempVo;
import com.baizhitong.resource.model.vo.temp.TeacherTempVo;
import com.baizhitong.resource.model.vo.temp.adminClassSubjectTempVo;
import com.baizhitong.resource.model.vo.temp.adminClassVo;

/**
 * 测试数据MySql接口实现
 * 
 * @author creator cuidc 2015/12/01
 * @author updater
 */
// @Service("demoMySqlDao")
public class DemoMySqlDaoImpl extends BaseMySqlDao<Demo, Integer> implements DemoDao {

    private Logger logger = LoggerFactory.getLogger(DemoMySqlDaoImpl.class);

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
            super.deleteByPK(2);
            super.doDelete(1);
            super.doDelete(tableName, pkName, pkValue);
            // super.doLoad(pkValue, rowMapper)
            // super.doLoad(tableName, pkName, pkValue, rowMapper)
            super.doUpdate(pkValue, params);
            super.doUpdate(pkName, pkValue, params);
            super.doUpdate(tableName, pkName, pkValue, params);
            super.exists(3);
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
            super.forceUpdate(pkValue, params);

            super.get(3);
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

    }

    @Override
    public void insertTeacher(TeacherTempVo teacher) {

    }

    @Override
    public void insertAdminClass(adminClassVo adminClass) {

    }

    @Override
    public void insertTeacherSubjectClass(adminClassSubjectTempVo adminClassSubject) {

    }

    @Override
    public int updateOrgUpdateBatch(String orgGuid, Integer batch) {
        return 0;
    }

    @Override
    public Map<String, Object> getOrgUpdateBatch(String orgGuid) {
        return null;
    }

    @Override
    public void insertInite(String orgGuid, Integer batch) {

    }

    @Override
    public void deleteAll(String orgCode) {

    }

    @Override
    public void insertTest() {

    }
}