package com.tianchi;


import com.tianchi.lucene.AnalysisUtil;
import com.tianchi.lucene.IndexUtil;
import com.tianchi.lucene.SearchUtil;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Field;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by houxiang on 15/7/30.
 */
public class MainTest {
    public static void main(String[] args){
        HelloLucene hl = new HelloLucene();
        hl.index();
        hl.search();
    }
    @Test
    public void test(){
        IndexUtil iu = new IndexUtil();
        iu.index();
    }
    @Test
    public void query(){
        IndexUtil iu = new IndexUtil();
        //iu.indexReader();
    }
    @Test
    public void delete(){
        IndexUtil iu = new IndexUtil();
        iu.delIndex();
    }
    @Test
    public void search(){
        IndexUtil iu = new IndexUtil();
        iu.search();
    }
    @Test
    public void indexSearch(){
        SearchUtil su = new SearchUtil();
        su.searchByTerm("content", "like", 6);
    }
    @Test
    public void analysis() throws IOException {
        Analyzer a1 = new StandardAnalyzer(Version.LUCENE_35);
        Analyzer a2 = new StopAnalyzer(Version.LUCENE_35);
        Analyzer a3 = new SimpleAnalyzer(Version.LUCENE_35);
        Analyzer a4 = new WhitespaceAnalyzer(Version.LUCENE_35);
        Analyzer a5 = new IKAnalyzer(true);
        String text = "打脸]前天确认报名。。。。今天卖独家内部资料的电话就来了。。。。";

        /**
         * StringReader re = new StringReader(str);
         IKSegmenter ik = new IKSegmenter(re,true);
         Lexeme lex = null;
         while((lex=ik.next())!=null){
         System.out.print(lex.getLexemeText()+"|");
         }
         }

         */
        Map<String, Integer> wordsFEN = new HashMap<String , Integer>();
        StringReader re = new StringReader(text);
        IKSegmenter ik = new IKSegmenter(re,true);
        Lexeme lex = null ;
        while ((lex = ik.next())!= null){
            System.out.println(lex.getLexemeText()+"|");
        }
       // AnalysisUtil.displayToken(text,a5);

    }
}
