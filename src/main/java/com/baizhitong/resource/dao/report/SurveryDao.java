package com.baizhitong.resource.dao.report;

import java.util.List;
import java.util.Map;

public interface SurveryDao {

    List<Map<String, Object>> getSurvery(Map param);

}