package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.LikesDao;
import com.liangxunwang.unimanager.model.HappyHandLike;
import com.liangxunwang.unimanager.query.LikesQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/3/3.
 */
@Service("likesService")
public class LikesService implements ListService,SaveService,DeleteService,ExecuteService,UpdateService {
    @Autowired
    @Qualifier("likesDao")
    private LikesDao likesDao;

    @Override
    public Object list(Object object) throws ServiceException {
        LikesQuery query = (LikesQuery) object;
        Map<String, Object> map = new HashMap<String, Object>();
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getSize();

        map.put("index", index);
        map.put("size", size);

        if(!StringUtil.isNullOrEmpty(query.getIs_use())){
            map.put("is_use", query.getIs_use());
        }

        List<HappyHandLike> lists = likesDao.lists(map);
        long count = likesDao.count(map);

        return new Object[]{lists, count};
    }


    @Override
    public Object save(Object object) throws ServiceException {
        HappyHandLike level = (HappyHandLike) object;
        level.setLikeid(UUIDFactory.random());
        level.setIs_use("1");
        likesDao.save(level);
        return null;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        String likeid = (String) object;
        likesDao.delete(likeid);
        return null;
    }

    @Override
    public Object execute(Object object) throws ServiceException {
        return likesDao.findById((String) object);
    }

    @Override
    public Object update(Object object) {
        HappyHandLike happyHandLike = (HappyHandLike) object;
        likesDao.update(happyHandLike);
        return 200;
    }

}
