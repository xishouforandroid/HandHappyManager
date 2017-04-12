package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.PhotosDao;
import com.liangxunwang.unimanager.model.HappyHandPhoto;
import com.liangxunwang.unimanager.query.PhotosQuery;
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
@Service("photosService")
public class PhotosService implements ListService,SaveService,DeleteService,ExecuteService,UpdateService {
    @Autowired
    @Qualifier("photosDao")
    private PhotosDao photosDao;

    @Override
    public Object list(Object object) throws ServiceException {
        PhotosQuery query = (PhotosQuery) object;
        Map<String, Object> map = new HashMap<String, Object>();
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getSize();

        map.put("index", index);
        map.put("size", size);

        if(!StringUtil.isNullOrEmpty(query.getEmpid())){
            map.put("empid", query.getEmpid());
        }
        if(!StringUtil.isNullOrEmpty(query.getNickname())){
            map.put("nickname", query.getNickname());
        }
        List<HappyHandPhoto> lists = photosDao.lists(map);
        long count = photosDao.count(map);
        return new Object[]{lists, count};
    }


    @Override
    public Object save(Object object) throws ServiceException {
        HappyHandPhoto level = (HappyHandPhoto) object;
        level.setPhotoid(UUIDFactory.random());
        level.setDateline(System.currentTimeMillis()+"");
        photosDao.save(level);
        return null;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        String photoid = (String) object;
        photosDao.delete(photoid);
        return null;
    }

    @Override
    public Object execute(Object object) throws ServiceException {
        return photosDao.findById((String) object);
    }

    @Override
    public Object update(Object object) {
        HappyHandPhoto happyHandLike = (HappyHandPhoto) object;
        photosDao.update(happyHandLike);
        return 200;
    }

}
