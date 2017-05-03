package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.HappyHandCxrz;
import com.liangxunwang.unimanager.model.HappyHandHyrz;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("cxrzDao")
public interface CxrzDao {

    /**
     * 查询
     */
    List<HappyHandCxrz> lists(Map<String, Object> map);
    long count(Map<String, Object> map);

    //保存
    void save(HappyHandCxrz happyHandCxrz);

    /**
     * 更新
     * @param happyHandCxrz
     */
    public void update(HappyHandCxrz happyHandCxrz);

    public void updateByEmpId(HappyHandCxrz happyHandCxrz);

}
