package com.tianchi.onex;

import com.tianchi.dao.FlowDao;
import com.tianchi.util.AnalysisUtil;
import com.tianchi.util.SplitWord;
import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

/**
 * Created by houxiang on 15/8/13.
 */
@Component
public class One {
    @Resource
    private FlowDao flowDao ;
    private List<Record> records = new ArrayList<>();

//    public void dataHandler(){
//        int beign = 0 ;
//        int limition = 1000 ;
//        while (beign < 275331){
//            beign += limition ;
//            List<Map<String,Object>> rawData = flowDao.selData(beign, limition);
//            for(Map<String , Object> unitMap : rawData) {
//
//                String content = (String) unitMap.get("content");
//                System.out.println(content);
//                String uid = (String) unitMap.get("uid");
//                String mid = (String) unitMap.get("mid");
//                Record record = new Record(uid, mid, content);
//                List<Map<String, Object>> testData = flowDao.selWeight(record.getUid());
//                double weight = SplitWord.keyAnaly(content);
//                System.out.println(weight);
//                Map<String, Double> dataPool = new TreeMap<>();
//                for (Map<String, Object> unit : testData) {
//                    double dist = Math.abs((double) unit.get("weight") - weight);
//                    String testMid = (String) unit.get("mid");
//                    dataPool.put(testMid, dist);
//
//                }
//                List<Map.Entry<String, Double>> comparUtil = new ArrayList<>(dataPool.entrySet());
//                System.out.println(comparUtil);
//                Collections.sort(comparUtil, new Comparator<Map.Entry<String, Double>>() {
//                    @Override
//                    public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
//                        double flag = (o1.getValue() - o2.getValue());
//                        if (flag < 0) {
//                            return -1;
//                        } else if (flag > 0) {
//                            return 1;
//                        } else {
//                            return 0;
//                        }
//                    }
//                });
//                System.out.println(comparUtil);
//                if (comparUtil.isEmpty()){
//                    continue;
//                }
//                Map.Entry<String , Double> ans = comparUtil.get(0);
//                String ansMid = ans.getKey();
//                System.out.println(ansMid);
//                List<Integer> args = new LinkedList<>();
//                Map<String , Object> ansUnit = flowDao.selArgs(ansMid) ;
//
//                System.out.println(ansUnit);
//                args.add((int) ansUnit.get("forward_count"));
//                args.add((int) ansUnit.get("comment_count"));
//                args.add((int) ansUnit.get("like_count"));
//                System.out.println(args);
//                record.setArgs(args);
//                System.out.println(record);
//                records.add(record);
//                fileUtil(record.toString());
//            }
//
//
//        }
//
//    }
//
    public void dataHandler(){
        List <RowMessage> list = null ;
        int begin = 0 ;
        int limition = 1000;
        while (begin<265042){
            list = flowDao.selFinalData(begin , limition);
            for (RowMessage mes : list){
                //System.out.println(mes.toString());
                fileUtil(mes.toString());
            }
            begin+= limition ;
        }
        list = flowDao.selFinalData(begin,42);
        for (RowMessage mes : list){
            fileUtil(mes.toString());
        }
    }
    public void test(){
        System.out.println(flowDao.selFinalData(0, 1000));
    }
    public void fileUtil(String str){
        File file = new File("/Users/houxiang/Desktop/result/result_9_8.txt");
        try {
            FileUtils.writeStringToFile(file,str,true);
        }catch (Exception e ){
            e.printStackTrace();
        }

    }
    public void Test(){
//        List<Integer> list = flowDao.selArgs("642ff4c7640ec78e9de2100914bdaaef");
//        System.out.println(list);
    }
    public static void main(String args[]){
        ApplicationContext as = new ClassPathXmlApplicationContext( "classpath:/spring/applicationContext*.xml");
        One one = (One)as.getBean(One.class);
        one.dataHandler();
    }
}
