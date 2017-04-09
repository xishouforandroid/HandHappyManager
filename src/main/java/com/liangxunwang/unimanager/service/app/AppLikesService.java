package com.liangxunwang.unimanager.service.app;

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
@Service("appLikesService")
public class AppLikesService implements ListService{
    @Autowired
    @Qualifier("likesDao")
    private LikesDao likesDao;

    @Override
    public Object list(Object object) throws ServiceException {
        List<HappyHandLike> lists = likesDao.listsAll();
        return lists;
    }

}
