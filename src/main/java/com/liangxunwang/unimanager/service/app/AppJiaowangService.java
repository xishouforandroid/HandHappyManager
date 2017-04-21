package com.liangxunwang.unimanager.service.app;


import com.liangxunwang.unimanager.dao.EmpDao;
import com.liangxunwang.unimanager.dao.JiaowangDao;

import com.liangxunwang.unimanager.model.Emp;
import com.liangxunwang.unimanager.model.HappyHandJw;
import com.liangxunwang.unimanager.query.FriendsQuery;
import com.liangxunwang.unimanager.query.JiaowangQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.DateUtil;
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
@Service("appJiaowangService")
public class AppJiaowangService implements SaveService,ListService,UpdateService,DeleteService {
    @Autowired
    @Qualifier("jiaowangDao")
    private JiaowangDao jiaowangDao;

    @Autowired
    @Qualifier("empDao")
    private EmpDao empDao;

    @Override
    public Object save(Object object) throws ServiceException {
        HappyHandJw happyHandJw = (HappyHandJw) object;
        if(StringUtil.isNullOrEmpty(happyHandJw.getEmpid1())){
            throw new ServiceException("empId1Null");
        }
        if(StringUtil.isNullOrEmpty(happyHandJw.getEmpid2())){
            throw new ServiceException("empId2Null");
        }
        //先判断是否已经有两个人的交往申请了
        Map<String, Object> map = new HashMap<>();
        map.put("empid1", happyHandJw.getEmpid1() );
        map.put("empid2", happyHandJw.getEmpid2() );
        map.put("is_check", "0" );
        List<HappyHandJw> list1 = jiaowangDao.lists(map);
        if(list1 != null && list1.size() > 0){
            throw new ServiceException("hasApply");//对方尚未确认，请耐心等待
        }
        //第二层判断
        Emp emp1 = empDao.findById(happyHandJw.getEmpid1());
        if(emp1 != null && "2".equals(emp1.getState())){
            throw new ServiceException("youHasJwdx");//您已经有交往对象了
        }
        Emp emp2= empDao.findById(happyHandJw.getEmpid2());
        if(emp2 != null && "2".equals(emp2.getState())){
            throw new ServiceException("sheHasJwdx");//对方已经有交往对象了
        }

        happyHandJw.setApplytime(System.currentTimeMillis() + "");
        happyHandJw.setIs_check("0");
        happyHandJw.setJwid(UUIDFactory.random());
        jiaowangDao.save(happyHandJw);
        return null;
    }

    @Override
    public Object list(Object object) throws ServiceException {
        JiaowangQuery query = (JiaowangQuery) object;
        Map<String, Object> map = new HashMap<>();
        if(!StringUtil.isNullOrEmpty(query.getEmpid1())){
            map.put("empid1", query.getEmpid1());
        }
        if(!StringUtil.isNullOrEmpty(query.getEmpid2())){
            map.put("empid2", query.getEmpid2());
        }
        if(!StringUtil.isNullOrEmpty(query.getIs_check())){
            map.put("is_check", query.getIs_check());
        }
        List<HappyHandJw> list = jiaowangDao.lists(map);
        for(HappyHandJw member:list){
            if(member != null){
                if(!StringUtil.isNullOrEmpty(member.getEmpid1Cover())){
                    if (member.getEmpid1Cover().startsWith("upload")) {
                        member.setEmpid1Cover(Constants.URL + member.getEmpid1Cover());
                    }else {
                        member.setEmpid1Cover(Constants.QINIU_URL + member.getEmpid1Cover());
                    }
                }
                if(!StringUtil.isNullOrEmpty(member.getEmpid2Cover())){
                    if (member.getEmpid2Cover().startsWith("upload")) {
                        member.setEmpid2Cover(Constants.URL + member.getEmpid2Cover());
                    }else {
                        member.setEmpid2Cover(Constants.QINIU_URL + member.getEmpid2Cover());
                    }
                }
            }
        }
        return list;
    }


    //接受交往申请
    @Override
    public Object update(Object object) {
        HappyHandJw happyHandJw = (HappyHandJw) object;
        if(StringUtil.isNullOrEmpty(happyHandJw.getIs_check())){
            throw new ServiceException("ischecknull");
        }
        if(StringUtil.isNullOrEmpty(happyHandJw.getJwid())){
            throw new ServiceException("jwidisnull");
        }
        if("1".equals(happyHandJw.getIs_check())){
            //统一交往  判断
            //判断对方是否已经在交往中了
            Emp emp1 = empDao.findById(happyHandJw.getEmpid1());
            if(emp1 != null && "2".equals(emp1.getState())){
                throw new ServiceException("sheHasJwdx");//对方已经有交往对象了
            }
            Emp emp2= empDao.findById(happyHandJw.getEmpid2());
            if(emp2 != null && "2".equals(emp2.getState())){
                throw new ServiceException("youHasJwdx");//您已经有交往对象了
            }
            happyHandJw.setStartime(System.currentTimeMillis()+"");
        }
        happyHandJw.setAccepttime(System.currentTimeMillis()+"");//处理时间

        jiaowangDao.update(happyHandJw);

        if("1".equals(happyHandJw.getIs_check())){
            //说明接受交往申请了
            HappyHandJw happyHandJw1 = new HappyHandJw();
            happyHandJw1.setJwid(UUIDFactory.random());
            happyHandJw1.setIs_check("1");
            happyHandJw1.setAccepttime(System.currentTimeMillis() + "");
            happyHandJw1.setApplytime(System.currentTimeMillis()+"");
            happyHandJw1.setEmpid1(happyHandJw.getEmpid2());
            happyHandJw1.setEmpid2(happyHandJw.getEmpid1());
            jiaowangDao.save(happyHandJw1);
            //更新会员表 申请人和被申请人都要处理
            empDao.updateState(happyHandJw.getEmpid1(), "2");
            empDao.updateState(happyHandJw.getEmpid2(), "2");
        }
        return 200;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        HappyHandJw happyHandJw = (HappyHandJw) object;
        jiaowangDao.delete(happyHandJw);
        return 200;
    }
}
