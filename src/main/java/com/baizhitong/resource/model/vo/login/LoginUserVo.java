package com.baizhitong.resource.model.vo.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.baizhitong.resource.model.share.ShareUserLogin;
import com.baizhitong.utils.DataUtils;

public class LoginUserVo {
    public String cd_guid;
    public String userCode;
    public String userName;

    public String getCd_guid() {
        return cd_guid;
    }

    public void setCd_guid(String cd_guid) {
        this.cd_guid = cd_guid;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static LoginUserVo EntityToVo(ShareUserLogin login) {
        LoginUserVo vo = new LoginUserVo();
        DataUtils.copySimpleObject(login, vo);
        return vo;
    }

    /**
     * 查询登录表的volist ()<br>
     * 
     * @param mapList
     * @return
     */
    public static List<LoginUserVo> mapListToVoList(List<Map<String, Object>> mapList) {
        if (mapList == null || mapList.size() < 0)
            return null;
        List<LoginUserVo> loginUserVos = new ArrayList<LoginUserVo>();
        for (Map<String, Object> map : mapList) {
            LoginUserVo userVo = new LoginUserVo();
            userVo.setCd_guid(MapUtils.getString(map, "cd_guid"));
            userVo.setUserCode(MapUtils.getString(map, "userCode"));
            userVo.setUserName(MapUtils.getString(map, "userName"));
            loginUserVos.add(userVo);
        }
        return loginUserVos;
    }

}
