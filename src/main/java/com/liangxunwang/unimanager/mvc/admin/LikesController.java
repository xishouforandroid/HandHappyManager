package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.model.HappyHandLike;
import com.liangxunwang.unimanager.query.LikesQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/likes")
public class LikesController extends ControllerConstants {

    @Autowired
    @Qualifier("likesService")
    private ListService likesServiceList;

    @Autowired
    @Qualifier("likesService")
    private SaveService likesServiceSave;

    @Autowired
    @Qualifier("likesService")
    private ExecuteService likesServiceExe;


    @Autowired
    @Qualifier("likesService")
    private DeleteService likesServiceDelete;

    @Autowired
    @Qualifier("likesService")
    private UpdateService likesServiceUpdate;

    @RequestMapping("list")
    public String list(ModelMap map, LikesQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        Object[] results = (Object[]) likesServiceList.list(query);
        map.put("list", results[0]);
        long count = (Long) results[1];
        page.setCount(count);
        page.setPageCount(calculatePageCount(query.getSize(), count));
        map.addAttribute("page", page);
        map.addAttribute("query", query);
        return "/likes/list";
    }

    @RequestMapping("toAdd")
    public String add(){
        return "/likes/add";
    }

    @RequestMapping("add")
    @ResponseBody
    public String add(HappyHandLike handLike){
        likesServiceSave.save(handLike);
        return toJSONString(SUCCESS);
    }

    @RequestMapping("delete")
    @ResponseBody
    public String delete(String likeid){
        likesServiceDelete.delete(likeid);
        return toJSONString(SUCCESS);
    }

    @RequestMapping("/toDetail")
    public String toDetail(HttpSession session,ModelMap map, String id) throws Exception {
        Admin manager = (Admin) session.getAttribute(ACCOUNT_KEY);
        HappyHandLike like = (HappyHandLike) likesServiceExe.execute(id);
        map.put("like", like);
        return "/likes/edit";
    }

    @RequestMapping("edit")
    @ResponseBody
    public String edit(HappyHandLike handLike){
        likesServiceUpdate.update(handLike);
        return toJSONString(SUCCESS);
    }

}
