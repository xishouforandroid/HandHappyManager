package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.EmpKu;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.query.EmpQuery;
import com.liangxunwang.unimanager.service.DeleteService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by zhl on 2015/8/12.
 */
@Controller
public class EmpController extends ControllerConstants {

    @Autowired
    @Qualifier("empService")
    private ListService empServiceList;


    @RequestMapping("/emp/toAdd")
    public String toAdd(){
        return "/emp/add";
    }

    @RequestMapping("/emp/list")
    public String list(HttpSession session,ModelMap map, EmpQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        Object[] results = (Object[]) empServiceList.list(query);
        map.put("list", results[0]);
        long count = (Long) results[1];
        page.setCount(count);
        page.setPageCount(calculatePageCount(query.getSize(), count));
        map.addAttribute("page", page);
        map.addAttribute("query", query);
        return "/emp/list";
    }

    @Autowired
    @Qualifier("empKuService")
    private SaveService empKuServiceSave;


    @RequestMapping(value = "/emp/add", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String add( EmpKu empKu){
        try {
            empKuServiceSave.save(empKu);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            String msg = e.getMessage();
            if (msg.equals("HAS_EXIST_MOBILE")){
                return toJSONString(new ErrorTip(1, "手机号已经存在了，添加失败！"));
            }else  if (msg.equals("MOBILE_NULL")){
                return toJSONString(new ErrorTip(1, "手机号为空，添加失败！"));
            }else  if (msg.equals("EMP_NULL")){
                return toJSONString(new ErrorTip(1, "数据为空，添加失败！"));
            } else{
                return toJSONString(new ErrorTip(1, "添加失败，请稍后重试！"));
            }
        }
    }

    @Autowired
    @Qualifier("empKuService")
    private ListService empKuServiceList;
    @RequestMapping("/emp/listku")
    public String listku(HttpSession session,ModelMap map, EmpQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        Object[] results = (Object[]) empKuServiceList.list(query);
        map.put("list", results[0]);
        long count = (Long) results[1];
        page.setCount(count);
        page.setPageCount(calculatePageCount(query.getSize(), count));
        map.addAttribute("page", page);
        map.addAttribute("query", query);
        return "/emp/listku";
    }



    @Autowired
    @Qualifier("empKuService")
    private DeleteService empKuServiceDelete;

    @RequestMapping(value = "/empku/deleteById", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String deleteById(String empkuid){
        try {
            empKuServiceDelete.delete(empkuid);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(new ErrorTip(1, "删除数据失败，请稍后重试！"));
        }
    }

}