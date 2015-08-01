package com.tianchi.onex;

import com.tianchi.dao.FlowDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by houxiang on 15/7/28.
 */
@Component
public class Main {
    @Resource
    private  FlowDao flowDao;
    public static List<RowMessage> list = new ArrayList<RowMessage>();

    public static Map<String,Integer> freword = new HashMap<String, Integer>();

    public static int count = 0 ;
    public void readTxtFile(String filepath){

        RowMessage ms = new RowMessage();
        try {
            String encoding = "UTF-8";
            File file = new File(filepath);
           if(file.exists() && file.isFile()){
                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String txtLine = null;
                while((txtLine = bufferedReader.readLine())!=null){
                    StringReader re = new StringReader(txtLine);
                    IKSegmenter is = new IKSegmenter(re,true);
                    Lexeme lexeme = null ;
                    while ((lexeme = is.next())!=null){
                        if(freword.containsKey(lexeme.getLexemeText())){
                            freword.put(lexeme.getLexemeText(),freword.get(lexeme.getLexemeText())+1);
                        }else {
                            freword.put(lexeme.getLexemeText(),1);
                        }
                    }
                }

                read.close();
            }else{
                System.out.println("找不到指定文件");
            }
        }catch (Exception e ){
            e.printStackTrace();
        }

        Iterator<Map.Entry<String , Integer>> itr = freword.entrySet().iterator();
        while (itr.hasNext()){
            Map.Entry<String,Integer> entry = itr.next();
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key+"****"+value);

            flowDao.insertWord(key,value);
        }

    }

    public void dataHandle( ) throws  IOException{
        int BeginId = 1;
        int endId = BeginId +1000;
        while (endId < 2000){
            List<String> ans = flowDao.selData("weibo_train_data",BeginId,endId);
            for (String str : ans){
                StringReader re = new StringReader(str);
                IKSegmenter is = new IKSegmenter(re,true);
                Lexeme lexeme = null ;
                while ((lexeme = is.next())!=null){
                    if(freword.containsKey(lexeme.getLexemeText())){
                        freword.put(lexeme.getLexemeText(),freword.get(lexeme.getLexemeText())+1);
                    }else {
                        freword.put(lexeme.getLexemeText(),1);
                    }
                }
            }
            BeginId +=1000;
            endId = BeginId+1000;
            System.out.println(endId);
        }
        Iterator<Map.Entry<String,Integer>> itr = freword.entrySet().iterator();
        while (itr.hasNext()){
            String key = itr.next().getKey();
            Integer value = itr.next().getValue();
            flowDao.insertWord(key,value);
        }
    }


    public void readFileByByte(String path){
        RowMessage ms = new RowMessage();
        File file = new File(path);
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] msArray = new byte[1024*10124*10];
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            int length = 0;
            while ((length = in.read(msArray))!=-1){
                System.out.println(length);
                String str = new String(msArray,0,length);
                System.out.println(str);
                String[] tmp1 = str.split("\n");

                for(String s: tmp1){
                    System.out.println(s);
                    String[] tmp = s.split("\t");
                    ms.setUid(tmp[0]);
                    ms.setMid(tmp[1]);
                    ms.setTime(tmp[2]);
                    ms.setForward_count(Integer.parseInt(tmp[3]));
                    ms.setComment_count(Integer.parseInt(tmp[4]));
                    ms.setLike_count(Integer.parseInt(tmp[5]));
                    ms.setContent(tmp[6]);
                    int ans = flowDao.insertLog(ms.getUid(), ms.getMid(), ms.getTime(), ms.getContent(), ms.getForward_count(), ms.getComment_count(), ms.getLike_count());

                }

            }
        }catch (Exception e ){
            e.printStackTrace();
        }

    }

    public void wordCount(){
        List<Record> recordArray = new ArrayList<Record>();
        Record record = new Record();
        int sum = 27000;
        int beginId = 0 ;
        while (beginId < sum){
           // List<Map<String,Object>> ans = flowDao.selData();

        }

    }
    public ArrayList<String> segWord(String content){

        return null ;

    }

    public static void main(String[] args) throws  IOException{

        //ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
         //       "classpath:/spring/applicationContext*.xml");
        ApplicationContext as = new ClassPathXmlApplicationContext( "classpath:/spring/applicationContext*.xml");

        String path = "/Users/houxiang/Desktop/result/text.txt";
        Main app = as.getBean(Main.class);
        //app.readTxtFile(path);
       // app.readFileByByte(path);
        app.dataHandle();

    }
}
