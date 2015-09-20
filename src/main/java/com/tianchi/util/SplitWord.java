package com.tianchi.util;


import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.ansj.util.FilterModifWord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by houxiang on 15/8/7.
 */
public class SplitWord {
    private static Set<String> stopwords = new HashSet<>();
    private static Logger logger = Logger.getLogger(SplitWord.class);
    static {

       try {
           File file = FileUtils.getFile("/Users/houxiang/workspace/weibopre/src/main/resources/","stopword.dic");
           LineIterator litr = FileUtils.lineIterator(file);
           while (litr.hasNext()){
               stopwords.add(litr.next());
           }
       }catch (IOException e ){
           e.printStackTrace();
       }
    }

//
//    public static void main(String[] args) throws IOException {
//        for(String s : stopwords){
//            System.out.println(s);
//        }
//        File file = new File("/Users/houxiang/Desktop/weibo_train_data.txt");
//        File targetFile = new File("/Users/houxiang/Desktop/result/result.txt");
//        LineIterator itr = FileUtils.lineIterator(file);
//        while (itr.hasNext()){
//            String str = itr.next();
//            String title = null;
//            String[] tmp = str.split("\t");
//            String ans = "" ;
//            String content =tmp[tmp.length - 1];
//
//           // logger.info(tmp.length);
//            System.out.println(tmp[1]);
//            String contentAnalys = Main.ananly(content);
//            logger.info(contentAnalys);
//            double sum = Main.keyAnaly(content);
//            String ansList ="" ;
//            for(String s : tmp){
//               ansList += s + "\t" ;
//            }
//            ansList+= contentAnalys+"\t" ;
//            ansList+= String.valueOf(sum)+"\n";
//            FileUtils.write(targetFile,ansList,true);
//        }
//
//    }

    /**
     * a 形容词
     ad 副形词
     an 名形词
     ag 形容词性语素
     al 形容词性惯用语
     * @param str
     * @return
     */
    public static String ananly(String str){
        FilterModifWord.insertStopWord("...", "表情");
        FilterModifWord.insertStopNatures("a", "ad", "ag", "al");




        KeyWordComputer kwc = new KeyWordComputer(5);
        double sum = 0.0;
        String content = str;
        Collection<Keyword> result = kwc.computeArticleTfidf( content);
        Iterator<Keyword> itr = result.iterator();
//        for(Keyword k : result){
//            if(stopwords.contains(k.getName())){
//                result.remove(k);
//                continue;
//            }
//            sum += k.getScore();
//        }
        while (itr.hasNext()){
            if(stopwords.contains(itr.next().getName())){
                itr.next();
                itr.remove();
            }
            sum+= itr.next().getScore();
        }
        //System.out.println(result);
        return result.toString();

    }
    public static double keyAnaly(String str){
        FilterModifWord.insertStopWord("...","表情");
        FilterModifWord.insertStopNatures("a", "ad", "ag", "al");
        KeyWordComputer kwc = new KeyWordComputer(5);
        double sum = 0.0;
        String content = str;
        Collection<Keyword> result = kwc.computeArticleTfidf( content);
        for(Keyword k : result){
            sum += k.getScore();
        }
        //System.out.println(result+"---->"+sum);
        return  sum ;
    }
    public static  void  main(String[] args){
        System.out.println(keyAnaly("[doge]2015首胜已拿，烧烤ing"));
    }
}
