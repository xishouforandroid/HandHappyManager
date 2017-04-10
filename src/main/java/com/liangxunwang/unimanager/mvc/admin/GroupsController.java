package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.model.HappyHandGroup;
import com.liangxunwang.unimanager.model.HappyHandLike;
import com.liangxunwang.unimanager.query.GroupsQuery;
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
@RequestMapping("/groups")
public class GroupsController extends ControllerConstants {

    @Autowired
    @Qualifier("groupsService")
    private ListService groupsServicelist;

    @Autowired
    @Qualifier("groupsService")
    private SaveService groupsServiceSave;

    @Autowired
    @Qualifier("groupsService")
    private ExecuteService groupsServiceExe;


    @Autowired
    @Qualifier("groupsService")
    private DeleteService groupsServiceDel;

    @Autowired
    @Qualifier("groupsService")
    private UpdateService groupsServiceUpdate;

    @Autowired
    @Qualifier("groupsService")
    private FindService groupsServiceFind;


    @RequestMapping(value = "/list", produces = "text/plain;charset=UTF-8")
    public String list(ModelMap map, GroupsQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        Object[] results = (Object[]) groupsServicelist.list(query);
        map.put("list", results[0]);
        long count = (Long) results[1];
        page.setCount(count);
        page.setPageCount(calculatePageCount(query.getSize(), count));
        map.addAttribute("page", page);
        map.addAttribute("query", query);
        return "/groups/list";
    }

    @Autowired
    @Qualifier("likesService")
    private ExecuteService likesService;

    @RequestMapping(value = "/toAdd", produces = "text/plain;charset=UTF-8")
    public String add(ModelMap map, String id) throws Exception {
        //根据likeid查询是否存在群组了
        HappyHandGroup group = (HappyHandGroup) groupsServiceFind.findById(id);
        if(group != null){
            map.put("like", group);
            return "/groups/edit";
        }else{
            HappyHandLike like = (HappyHandLike) likesService.execute(id);
            map.put("like", like);
            return "/groups/add";
        }

    }

    @RequestMapping(value = "/add", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String add(HappyHandGroup handLike){
        groupsServiceSave.save(handLike);
        return toJSONString(SUCCESS);
    }

    @RequestMapping(value = "/delete", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String delete(String likeid){
        groupsServiceDel.delete(likeid);
        return toJSONString(SUCCESS);
    }

//    likesService
    @RequestMapping(value = "/toDetail", produces = "text/plain;charset=UTF-8")
    public String toDetail(HttpSession session,ModelMap map, String id) throws Exception {
        Admin manager = (Admin) session.getAttribute(ACCOUNT_KEY);
        //根据likeid查询是否已经存在该群组了

        HappyHandGroup like = (HappyHandGroup) groupsServiceExe.execute(id);
        map.put("like", like);
        return "/groups/edit";
    }

    @RequestMapping(value = "/edit", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String edit(HappyHandGroup handLike){
        groupsServiceUpdate.update(handLike);
        return toJSONString(SUCCESS);
    }

    //非兴趣爱好群添加
    @RequestMapping(value = "/toAddGroups", produces = "text/plain;charset=UTF-8")
    public String toAddGroups(ModelMap map, String id) throws Exception {
        HappyHandLike like = new HappyHandLike();
        map.put("like", like);
        return "/groups/add";
    }

}
