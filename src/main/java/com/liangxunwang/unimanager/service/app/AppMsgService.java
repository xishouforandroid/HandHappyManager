package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.JiaowangDao;
import com.liangxunwang.unimanager.dao.MessagesDao;
import com.liangxunwang.unimanager.dao.NewsDao;
import com.liangxunwang.unimanager.dao.NoticeDao;
import com.liangxunwang.unimanager.model.*;
import com.liangxunwang.unimanager.query.MessagesQuery;
import com.liangxunwang.unimanager.service.ExecuteService;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.RelativeDateFormat;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("appMsgService")
public class AppMsgService implements ListService{

    @Autowired
    @Qualifier("messagesDao")
    private MessagesDao messagesDao;

    @Autowired
    @Qualifier("newsDao")
    private NewsDao newsDao;

    @Autowired
    @Qualifier("noticeDao")
    private NoticeDao noticeDao;

    @Autowired
    @Qualifier("jiaowangDao")
    private JiaowangDao jiaowangDao;

    @Override
    public Object list(Object object) throws ServiceException {
        String empid = (String) object;
        //系统消息
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("empid", empid);
        List<HappyHandMessage> lists1 = messagesDao.lists(map1);
        for(HappyHandMessage happyHandMessage:lists1){
            if(!StringUtil.isNullOrEmpty(happyHandMessage.getDateline())){
                happyHandMessage.setDateline(RelativeDateFormat.format(Long.parseLong(happyHandMessage.getDateline())));
            }
        }

        //系统资讯
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("index", 0);
        map2.put("size", 10);
        List<HappyHandNews> lists2 = newsDao.lists(map2);

        //活动公告
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("index", 0);
        map3.put("size", 10);
        List<HappyHandNotice> lists3 = noticeDao.lists(map3);

        //交往消息
        Map<String, Object> map4 = new HashMap<>();
        map4.put("empid2", empid);
        map4.put("is_check", "0" );
        List<HappyHandJw> list4 = jiaowangDao.lists(map4);

        return new MsgCount(lists1,lists2, lists3,list4);
    }



}
