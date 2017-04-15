package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.HappyHandChoose;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("chooseDao")
public interface ChooseDao {
    void save(HappyHandChoose emp);

    List<HappyHandChoose> lists(Map<String, Object> map);

    long count(Map<String, Object> map);

    HappyHandChoose findById(String chooseid);

    HappyHandChoose findByEmpid(String empid);

    void update(HappyHandChoose happyHandChoose);
}
