package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.HappyHandLike;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class AppLikesController extends ControllerConstants {

    @Autowired
    @Qualifier("appLikesService")
    private ListService appLikesService;

    @Autowired
    @Qualifier("appLikesService")
    private ExecuteService appLikesServiceExe;

    @RequestMapping(value = "/appLikes", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appLikes(){
        try {
            List<HappyHandLike> lists = (List<HappyHandLike>) appLikesService.list("");
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }

    @RequestMapping(value = "/appLikesBylikeIds", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String appLikesBylikeIds(String likeids){
        try {
            List<HappyHandLike> lists = (List<HappyHandLike>) appLikesServiceExe.execute(likeids);
            DataTip tip = new DataTip();
            tip.setData(lists);
            return toJSONString(tip);
        }catch (Exception e){
            return toJSONString(new ErrorTip(1, "获取数据失败，请稍后重试！"));
        }
    }

}
