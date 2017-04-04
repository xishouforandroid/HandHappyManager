package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("adminDao")
public interface AdminDao {
    void add(Admin admin);

    void delete(String id);

    /**
     * 根据ID修改密码
     * @param manager_id
     * @param manager_pass
     */
    void updatePass(@Param(value = "manager_id")String manager_id, @Param(value = "manager_pass") String manager_pass);

    /**
     * 查询所有的后台管理员信息
     */
    List<Admin> lists(Map<String, Object> map);

    long count(Map<String,Object> map);

    Admin findById(String manager_id);

    Admin findByName(String manager_admin);
    /**
     * 根据ID修改状态
     * @param is_use
     * @param manager_id
     */
    void updateStatus(@Param(value = "manager_id")String manager_id, @Param(value = "is_use") String is_use);
}
