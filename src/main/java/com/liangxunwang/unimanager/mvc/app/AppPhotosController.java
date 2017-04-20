package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.HappyHandPhoto;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.query.EmpQuery;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class AppPhotosController extends ControllerConstants {

    @Autowired
    @Qualifier("photosService")
    private SaveService photosServiceSave;

    @Autowired
    @Qualifier("photosService")
    private UpdateService photosServiceUpdate;

    @Autowired
    @Qualifier("photosService")
    private FindService photosServiceFind;

    @RequestMapping(value = "/appSaveOrUpdatePhotos", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appSaveOrUpdatePhotos(HappyHandPhoto happyHandPhoto){
        if(StringUtil.isNullOrEmpty(happyHandPhoto.getEmpid())){
            return toJSONString(new ErrorTip(1, "请检查用户ID是否存在！"));
        }
        if(StringUtil.isNullOrEmpty(happyHandPhoto.getPhotos())){
            return toJSONString(new ErrorTip(1, "请检查图片是否为空！"));
        }
        try {
            List<HappyHandPhoto> lists = (List<HappyHandPhoto>) photosServiceFind.findById(happyHandPhoto.getEmpid());
            if(lists != null && lists.size() > 0){
                //说明存在了 更新
                HappyHandPhoto photo = lists.get(0);//原先的相册
                photo.setPhotos(photo.getPhotos()+","+happyHandPhoto.getPhotos());
                photosServiceUpdate.update(photo);
            }else {
                //添加
                photosServiceSave.save(happyHandPhoto);
            }
            DataTip tip = new DataTip();
            tip.setData(SUCCESS);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "添加相册数据失败，请稍后重试！"));
        }
    }



    @Autowired
    @Qualifier("appPhotosService")
    private ListService appPhotosServiceList;

    @RequestMapping(value = "/appPhotos", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appPhotos(EmpQuery query){
        try {
            List<HappyHandPhoto> lists = (List<HappyHandPhoto>) appPhotosServiceList.list(query);
            HappyHandPhoto happyHandPhoto = new HappyHandPhoto();
            if(lists != null && lists.size()>0){
                happyHandPhoto = lists.get(0);
            }
            DataTip tip = new DataTip();
            tip.setData(happyHandPhoto);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "添加相册数据失败，请稍后重试！"));
        }
    }

}
