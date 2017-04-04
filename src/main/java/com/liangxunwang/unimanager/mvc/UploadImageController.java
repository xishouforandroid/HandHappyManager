package com.liangxunwang.unimanager.mvc;

import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.model.tip.ErrorTip;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.ImageCompressUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhl on 2015/1/31.
 */
@Controller
public class UploadImageController extends ControllerConstants {
    @RequestMapping("/uploadImage")
    @ResponseBody
    public String uploadImage(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request){
        String path = null;
        try {
            path = init(request.getSession());
        } catch (ServletException e) {

        }
        String fileName = file.getOriginalFilename();
        fileName = fileName.toLowerCase();
        String newFileName = null;
        String compressName = null;
        if(fileName != null && !"".equals(fileName)) {
            String type = fileName.substring(fileName.lastIndexOf("."), fileName.length()); //上传文件类型
            //判断上传文件是否符合上传图片格式
            if(!".jpg".equals(type) && !".png".equals(type) && !".gif".equals(type) && !".bmp".equals(type) && !".jpeg".equals(type)  && !".3gp".equals(type)  && !".mp4".equals(type)) {
                return toJSONString(new ErrorTip(1, "上传文件格式不正确！"));
            }
            newFileName = String.valueOf(System.currentTimeMillis()) + fileName.subSequence(fileName.lastIndexOf("."), fileName.length());
            File targetFile = new File(path, newFileName);
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            //保存
            try {
                file.transferTo(targetFile);
                if (".jpg".equals(type) || ".jpeg".equals(type) || ".png".equals(type)|| ".bmp".equals(type)){
                    //如果是图片 对上传的图片进行压缩
                    compressName = "cp"+newFileName;
                    ImageCompressUtil.saveMinPhoto(path + newFileName, path + compressName, 10000, 1d);
                    //压缩完后将原图片删除
                    if (targetFile != null && targetFile.isFile()) {
                        targetFile.delete();
                    }
                }else {
                    compressName = newFileName;
                }
            } catch (Exception e) {
                return toJSONString(new ErrorTip(1, "上传文件失败！"));//上传文件失败，
            }
        } else {
            return toJSONString(new ErrorTip(1, "上传图片失败！"));//上传图片为空
        }
        DataTip dataTip = new DataTip();
        dataTip.setData("upload/"+getDatePath()+ "/" + compressName);
        return toJSONString(dataTip);
    }

    /**
     * 通过上下文来取工程路径
     *
     * @return
     * @throws Exception
     */
    private String getAbsolutePathByContext(HttpSession session) throws Exception {

        String webPath = session.getServletContext().getRealPath("/upload/"+getDatePath());

        webPath = webPath.replaceAll("[\\\\\\/]WEB-INF[\\\\\\/]classes[\\\\\\/]?", "/");
        webPath = webPath.replaceAll("[\\\\\\/]+", "/");
        webPath = webPath.replaceAll("%20", " ");

        if (webPath.matches("^[a-zA-Z]:.*?$")) {

        } else {
            webPath = "/" + webPath;
        }

        webPath += "/";
        webPath = webPath.replaceAll("[\\\\\\/]+", "/");
        return webPath;

    }

    /**
     * 通过类路径来取工程路径
     *
     * @return
     * @throws Exception
     */
    private String getAbsolutePathByClass(HttpSession session) throws Exception {
        String webPath = this.getClass().getResource("/upload").getPath().replaceAll("^\\/", "");
        webPath = webPath.replaceAll("[\\\\\\/]WEB-INF[\\\\\\/]classes[\\\\\\/]?", "/");
        webPath = webPath.replaceAll("[\\\\\\/]+", "/");
        webPath = webPath.replaceAll("%20", " ");

        if (webPath.matches("^[a-zA-Z]:.*?$")) {

        } else {
            webPath = "/" + webPath;
        }

        webPath += "/";
        webPath = webPath.replaceAll("[\\\\\\/]+", "/");

        return webPath;
    }
    private String getAbsolutePathByResource(HttpSession session) throws Exception {
        URL url = session.getServletContext().getResource("/upload");
        String path = new File(url.toURI()).getAbsolutePath();
        if (!path.endsWith("\\") && !path.endsWith("/")) {
            path += File.separator;
        }
        return path;
    }

    public String init(HttpSession session) throws ServletException {
        String webPath = null;
        try {
            webPath = getAbsolutePathByContext(session);
        } catch (Exception e) {
        }

        // 在weblogic 11g 上可能无法从上下文取到工程物理路径，所以改为下面的
        if (webPath == null) {
            try {
                webPath = getAbsolutePathByClass(session);
            } catch (Exception e) {
            }
        }

        if (webPath == null) {
            try {
                webPath = getAbsolutePathByResource(session);
            } catch (Exception e) {
            }
        }

//        System.out.println(webPath);
        return webPath;
    }

    private String getDatePath(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }
}
