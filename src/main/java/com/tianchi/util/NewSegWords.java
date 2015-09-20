package com.tianchi.util;

import com.tianchi.dao.FlowDao;
import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.ansj.util.FilterModifWord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * Created by houxiang on 15/8/7.
 */
@Component
public class NewSegWords {
    @Resource
    private FlowDao flowDao ;
    private File sourceFile = new File("/Users/houxiang/Desktop/tianchi/weibo_train_data.txt");

    private File targetFile = new File("/Users/houxiang/Desktop/tianchi/predict_result.txt");

    private static Set<String> stopwords = new HashSet<>();
  //  private static Logger logger = Logger.getLogger(Main.class);
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


    public static void main(String[] args) throws IOException {
        ApplicationContext as = new ClassPathXmlApplicationContext( "classpath:/spring/applicationContext*.xml");
        NewSegWords ns = as.getBean(NewSegWords.class);
        ns.dataFlow();
    }

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
        KeyWordComputer kwc = new KeyWordComputer(5);
        double sum = 0.0;
        String content = str;
        Collection<Keyword> result = kwc.computeArticleTfidf(content);
        for(Keyword k : result){
            sum += k.getScore();
        }
        //System.out.println(result+"---->"+sum);
        return  sum ;
    }

    public void dataFlow() throws  IOException{
        //String str = FileUtils.readFileToString(sourceFile,"utf-8");
        LineIterator litr = FileUtils.lineIterator(sourceFile);
        while (litr.hasNext()){
            String[] tmps = litr.next().split("\t");
            String mid = tmps[0];
            String uid =tmps[1];
            String time = tmps[2];
            int forward = Integer.valueOf(tmps[3]);
            int comment = Integer.valueOf(tmps[4]);
            int like = Integer.valueOf(tmps[5]);
            String content = tmps[6];
            flowDao.insertLog(uid,mid,time,content,forward,comment,like);
        }
    }

    public void iksegWords() throws IOException {
        File sourceFile = new File("/Users/houxiang/Desktop/tianchi/weibo_predict_data.txt");

        File targetFile = new File("/Users/houxiang/Desktop/tianchi/predict_result.txt");

        File targetFile2 = new File("/Users/houxiang/Desktop/weibo_train_data_result2.txt");

        Analyzer anal  = new IKAnalyzer(true);

        LineIterator litr = FileUtils.lineIterator(sourceFile);

        Map<String,Integer> freword = new HashMap<>();
        while(litr.hasNext()){
            freword.clear();
            StringBuilder sb = new StringBuilder();
            String str = litr.next();
            String[] tmps = str.split("\t");
            StringBuilder line = new StringBuilder();

            String content = tmps[tmps.length-1];
            for (int i = 0 ; i<tmps.length-1 ;i++) {
                line.append(tmps[i]);
                line.append("\t");
            }

            StringReader re = new StringReader(content);
            IKSegmenter is = new IKSegmenter(re,true);
            Lexeme lexeme = null ;
            while ((lexeme = is.next())!=null){
                if(freword.containsKey(lexeme.getLexemeText())){
                    freword.put(lexeme.getLexemeText(),freword.get(lexeme.getLexemeText())+1);
                }else {
                    freword.put(lexeme.getLexemeText(),1);
                }
            }

            List<Map.Entry<String,Integer>> list = new LinkedList<>(freword.entrySet());

            Set<String> sets = freword.keySet();

            Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return Integer.compare(o1.getValue(), o2.getValue());
                }
            });

            line.append(sets.toString() + "\n");
            //System.out.println(line.toString());
            FileUtils.writeStringToFile(targetFile,line.toString(),true);
        }

        /*
        while (litr.hasNext()){
            StringBuilder sb = new StringBuilder();
            String str = litr.next();
            String[] tmps = str.split("\t");
            StringBuilder line = new StringBuilder();

            for (int i = 0 ; i<2;i++) {
                line.append(tmps[i]);
                line.append("\t");
            }
            String content = tmps[tmps.length-1];
            if (content!=null && content !=""){
                StringReader reader = new StringReader(content);
                TokenStream ts = anal.tokenStream("", reader);
                CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);

                //遍历分词数据
                StringBuilder builder = new StringBuilder();
                while(ts.incrementToken()){
                    System.out.print(term.toString()+"|");
                    sb.append(term.toString()+"|");
                }
                line.append(sb.toString());
                reader.close();
            }
            FileUtils.writeStringToFile(targetFile,line.toString(),true);
        }
        */
    }
}
