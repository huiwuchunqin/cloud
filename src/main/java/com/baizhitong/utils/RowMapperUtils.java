package com.baizhitong.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class RowMapperUtils implements RowMapper<Map<String, Object>> {

    public static RowMapper<Map<String, Object>> instance = new RowMapperUtils();

    @Override
    public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<String, Object> row = new HashMap();
        int columnCount = rs.getMetaData().getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            row.put(rs.getMetaData().getColumnLabel(i + 1).toString(), rs.getObject(i + 1));
        }
        return row;
    }
}
