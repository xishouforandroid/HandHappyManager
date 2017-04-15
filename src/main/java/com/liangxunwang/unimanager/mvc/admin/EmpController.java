package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.EmpKu;
import com.liangxunwang.unimanager.model.HappyHandLike;
import com.liangxunwang.unimanager.model.Province;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.query.EmpQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;


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

    @Autowired
    @Qualifier("empService")
    private FindService empServiceFind;

    @Autowired
    @Qualifier("provinceService")
    private ListService provinceServiceList;


    @Autowired
    @Qualifier("appLikesService")
    private ExecuteService appLikesServiceExe;

    @RequestMapping("/emp/toEdit")
    public String toEdit(HttpSession session,ModelMap map, String empid) throws Exception {
        Emp member = (Emp) empServiceFind.findById(empid);
        map.put("cover", member.getCover());
        map.put("cardpic", member.getCardpic());
        if(!StringUtil.isNullOrEmpty(member.getCover())){
            if (member.getCover().startsWith("upload")) {
                member.setCover(Constants.URL + member.getCover());
            }else {
                member.setCover(Constants.QINIU_URL + member.getCover());
            }
        }
        if(!StringUtil.isNullOrEmpty(member.getCardpic())){
            if (member.getCardpic().startsWith("upload")) {
                member.setCardpic(Constants.URL + member.getCardpic());
            }else {
                member.setCardpic(Constants.QINIU_URL + member.getCardpic());
            }
        }
        map.put("emp", member);
        //查询省份
        List<Province> listProvinces = (List<Province>) provinceServiceList.list("");
        map.put("listProvinces", listProvinces);
        //查询该会员的兴趣爱好
        if(!StringUtil.isNullOrEmpty(member.getLikeids())){
            List<HappyHandLike> lists = (List<HappyHandLike>) appLikesServiceExe.execute(member.getLikeids());
            if(lists != null){
                String str = "";
                for(HappyHandLike happyHandLike:lists){
                    str = str+happyHandLike.getLikename()+",";
                }
                str = str.substring(0,str.length()-1);
                map.put("listsName", str);
            }

        }
        return "/emp/edit";
    }


    @Autowired
    @Qualifier("empUpdateService")
    private UpdateService empUpdateService;

    @RequestMapping(value = "/emp/edit", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String edit(Emp emp){
        try {
            empUpdateService.update(emp);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(new ErrorTip(1, "修改会员信息失败，请稍后重试！"));
        }
    }
}
