package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Province;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository("provinceDao")
public interface ProvinceDao {
    List<Province> list();
}
