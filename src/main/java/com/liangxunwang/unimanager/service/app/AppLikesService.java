package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.LikesDao;
import com.liangxunwang.unimanager.model.HappyHandLike;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhl on 2015/3/3.
 */
@Service("appLikesService")
public class AppLikesService implements ListService,ExecuteService{
    @Autowired
    @Qualifier("likesDao")
    private LikesDao likesDao;

    @Override
    public Object list(Object object) throws ServiceException {
        List<HappyHandLike> lists = likesDao.listsAll();
        return lists;
    }

    @Override
    public Object execute(Object object) throws Exception {
        String likeids = (String) object;
        List<HappyHandLike> lists = new ArrayList<>();
        if(!StringUtil.isNullOrEmpty(likeids)){
            String[] arras = likeids.split(",");
            if(arras != null){
                for(int i=0;i<arras.length;i++){
                    HappyHandLike like = likesDao.findById(arras[i]);
                    lists.add(like);
                }
            }
        }
        return lists;
    }
}
