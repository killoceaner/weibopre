package com.tianchi.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by houxiang on 15/7/31.
 */
public class AnalysisUtil {
    /**
     * 通过分词器来对传入的str进行分词。
     * @param str
     * @param analyzer
     */
    public static void displayToken(String str , Analyzer analyzer){
        try {
            TokenStream stream = analyzer.tokenStream("test", new StringReader(str));
            //创建一个属性，这个属性回到流中不断的去流中的分词
            CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);

            //类似于迭代器的原理，当流往前走 cta相当于一个碗来取；
            while (stream.incrementToken()){
                System.out.println("["+cta+"]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayAllTokenInfo(String str , Analyzer analyzer) throws IOException{
        TokenStream stream = analyzer.tokenStream("test",new StringReader(str));
        //保存词与词之间的位置
        PositionIncrementAttribute pia = stream.addAttribute(PositionIncrementAttribute.class);
        //保存每个词汇的增量
        OffsetAttribute oa = stream.addAttribute(OffsetAttribute.class);
        //CharTermAttribute 保存相应的词汇
        CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
        //TypeAttribute 保存相应分词的类型
        TypeAttribute ta = stream.addAttribute(TypeAttribute.class);

        for(;stream.incrementToken();){
            System.out.println(pia.getPositionIncrement());
            System.out.println(cta +"********"+ta.type());
        }
    }
}
