package com.tianchi.onex;

import com.tianchi.dao.FlowDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by houxiang on 15/7/28.
 */
@Component
public class Main {
    @Resource
    private  FlowDao flowDao;
    public static List<Message> list = new ArrayList<Message>();
    public static int count = 0 ;
    public void readTxtFile(String filepath){
 /*
        Message ms = new Message();
        try {
            String encoding = "UTF-8";
            File file = new File(filepath);
           if(file.exists() && file.isFile()){
                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String txtLine = null;
                while((txtLine = bufferedReader.readLine())!=null){
                    count++;
                    System.out.println("****************"+count);
                    //System.out.println(txtLine);
                    String[]tmp = txtLine.split("\t");
                    for (int i=0; i<tmp.length;i++){
                        System.out.println(tmp[i]);
                    }
                    ms.setUid(tmp[0]);
                    ms.setMid(tmp[1]);
                    ms.setTime(tmp[2]);
                    ms.setForward_count(Integer.parseInt(tmp[3]));
                    ms.setComment_count(Integer.parseInt(tmp[4]));
                    ms.setLike_count(Integer.parseInt(tmp[5]));
                    ms.setContent(tmp[6]);
                    int ans = flowDao.insertLog(ms.getUid(),ms.getMid(),ms.getTime(),ms.getContent(),ms.getForward_count(),ms.getComment_count(),ms.getLike_count());
                    if (ans == 0){
                        list.add(ms);
                    }

                }

                read.close();
            }else{
                System.out.println("找不到指定文件");
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
*/

    }

    public static void main(String[] args){

        //ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
         //       "classpath:/spring/applicationContext*.xml");
        ApplicationContext as = new ClassPathXmlApplicationContext( "classpath:/spring/applicationContext*.xml");

        String path = "/Users/houxiang/Desktop/weibo_train_data.txt";
        Main app = as.getBean(Main.class);
        app.readTxtFile(path);

       // readTxtFile("/Users/houxiang/Desktop/weibo_train_data.txt");
    }
}
