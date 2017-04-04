package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Emp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("empDao")
public interface EmpDao {
    void add(Emp emp);

    void updatePass(@Param(value = "empid") String empid, @Param(value = "password") String password);

    List<Emp> lists(Map<String, Object> map);

    long count(Map<String, Object> map);

    Emp findById(String empid);

    Emp findByMobile(String mobile);

    void updateStatus(@Param(value = "empid") String empid, @Param(value = "is_use") String is_use);
}
