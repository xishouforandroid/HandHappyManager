package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.PhotosDao;
import com.liangxunwang.unimanager.model.HappyHandPhoto;
import com.liangxunwang.unimanager.query.EmpQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.RelativeDateFormat;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/3/3.
 */
@Service("appPhotosService")
public class AppPhotosService implements ListService {
    @Autowired
    @Qualifier("photosDao")
    private PhotosDao photosDao;

    @Override
    public Object list(Object object) throws ServiceException {
        EmpQuery query = (EmpQuery) object;
        Map<String, Object> map = new HashMap<String, Object>();
        if(!StringUtil.isNullOrEmpty(query.getEmpid())){
            map.put("empid", query.getEmpid());
        }
        List<HappyHandPhoto> lists = photosDao.listsAll(map);

        if(lists != null){
            for (HappyHandPhoto record : lists){

                if(!StringUtil.isNullOrEmpty(record.getPhotos())){
                    //处理图片URL链接
                    StringBuffer buffer = new StringBuffer();
                    String[] pics = new String[]{};
                    if(record!=null && record.getPhotos()!=null){
                        pics = record.getPhotos().split(",");
                    }
                    if(query.getSize() !=0){
                        //size不等于0的时候
                        for (int i=0; i<(pics.length<query.getSize()?pics.length:query.getSize()); i++){
                            if (pics[i].startsWith("upload")) {
                                buffer.append(Constants.URL + pics[i]);
                                if (i < pics.length - 1) {
                                    buffer.append(",");
                                }
                            }else {
                                buffer.append(Constants.QINIU_URL + pics[i]);
                                if (i < pics.length - 1) {
                                    buffer.append(",");
                                }
                            }
                        }
                    }else {
                        //size=0的时候查询全部图片
                        for (int i=0; i<pics.length; i++){
                            if (pics[i].startsWith("upload")) {
                                buffer.append(Constants.URL + pics[i]);
                                if (i < pics.length - 1) {
                                    buffer.append(",");
                                }
                            }else {
                                buffer.append(Constants.QINIU_URL + pics[i]);
                                if (i < pics.length - 1) {
                                    buffer.append(",");
                                }
                            }
                        }
                    }

                    record.setPhotos(buffer.toString());
                }
                record.setDateline(RelativeDateFormat.format(Long.parseLong(record.getDateline())));
            }
        }
        return lists;
    }


}
