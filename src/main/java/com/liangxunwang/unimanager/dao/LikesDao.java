package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.HappyHandLike;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/1/29.
 */
@Repository("likesDao")
public interface LikesDao {

    /**
     * 查询建议
     */
    List<HappyHandLike> lists(Map<String, Object> map);
    long count(Map<String, Object> map);


    //保存
    void save(HappyHandLike happyHandLike);


    //删除
    void delete(String likeid);

    /**
     * 根据ID查找
     */
    public HappyHandLike findById(String likeid);

    void update(HappyHandLike happyHandLike);

    List<HappyHandLike> listsAll();
}
