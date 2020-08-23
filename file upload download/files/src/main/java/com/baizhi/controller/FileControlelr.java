package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.entity.UserFile;
import com.baizhi.service.UserFileService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("file")
public class FileControlelr {

    @Autowired
    private UserFileService userFileService;

    /**
     * 展示所有文件的信息
     * @return
     */
    @GetMapping("showAll")
    public String showAll(HttpSession session, Model model){
        //在登陆的session中，获取用户信息
        User user = (User)session.getAttribute("user");
        //根据id查询所有文件信息
        List<UserFile> userFiles = userFileService.findByUserId(user.getId());
        //存入作用域中
        model.addAttribute("files",userFiles);
        return "showAll";
    }

    /**
     *上传文件处理，并保存文件信息到数据库中
     */
    @PostMapping("upload")
    public String upload(MultipartFile aaa,HttpSession session){
        User user = (User)session.getAttribute("user");
        Integer userId = user.getId();
        //获取文件的原始名称
        String oldFileName = aaa.getOriginalFilename();
        //获取原始文件的后缀
        String extension = "."+FilenameUtils.getExtension(aaa.getOriginalFilename());
        //生成新的文件名称
        String newFileName = new SimpleDateFormat("YYYYMMddHHmmss").
                format(new Date())+
                UUID.randomUUID().toString().replace("-","")
                +extension;
        //文件大小
        Long size = aaa.getSize();
        //文件类型
        String type = aaa.getContentType();
        //处理文件上传
        String realPah = null;
        try {
            realPah = ResourceUtils.getURL("classpath:").getPath()+"/static/files";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //生成动态日期目录
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dataDirPath = realPah+"/"+dateFormat;
        File dateDir = new File(dataDirPath);
        //判断这个日期目录是否存在，不存在则创建
        if (!dateDir.exists()){
            dateDir.mkdirs();
        }
        //处理文件上传
        try {
            aaa.transferTo(new File(dateDir,newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将文件信息保存在数据库
        UserFile userFile = new UserFile();
        userFile.setOldFileName(oldFileName);
        userFile.setNewFileName(newFileName);
        userFile.setExt(extension);
        userFile.setSize(String.valueOf(size));
        userFile.setType(type);
        userFile.setPath("/files/"+dateFormat);
        userFile.setUserId(userId);
        userFileService.save(userFile);
        return "redirect:/file/showAll";
    }

    /**
     * 文件下载
     */
    @GetMapping("download")
    public String download(String openStyle,String id, HttpServletResponse response) throws IOException {
        //打开方式
        openStyle=openStyle==null?"atachment":openStyle;

        UserFile userFile = userFileService.findById(id);
        //根据文件信息中文件名和文件的存储路径
        String realPath = null;
        try {
            realPath = ResourceUtils.getURL("classpath:").getPath() + "/static" + userFile.getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File file = new File(realPath,userFile.getNewFileName());
        //获取文件输入流
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        response.setHeader("content-disposition",openStyle+";fileName="+userFile.getOldFileName());
        //获取响应输出流
        ServletOutputStream stream = null;
        try {
           stream= response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int len = 0;
        byte[] bytes = new byte[1024*1024];
        while((len = fis.read(bytes))!=-1){
            stream.write(bytes,0,len);
        }
        fis.close();
        stream.close();
        if ("atachment".equals(openStyle)){
            int downcounts = userFile.getDowncounts()+1;
            userFile.setDowncounts(userFile.getDowncounts()+1);
            userFileService.updateFilecounts(userFile);
        }
        return "redirect:/file/showAll";
    }
    /**
     * 删除
     */
    @GetMapping("delete")
    public String delete(String id){
        //获取文件信息
        UserFile userFile = userFileService.findById(id);
        //从服务器中删除文件
        String realPath = null;
        try {
            //获取路径
            realPath = ResourceUtils.getURL("classpath:").getPath()+"/static"+userFile.getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File file = new File(realPath ,userFile.getNewFileName());
        if (file.exists()){
            file.delete();
        }
        //删除数据库中的记录
        userFileService.delete(id);
        return "redirect:/file/showAll";
    }
}
