package com.tianchi.util;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by houxiang on 15/8/1.
 */
public class AnalysisUtil {
    public static void main(String[] args){
        List<Term> terms = ToAnalysis.parse("湛云大傻逼");
        System.out.println(terms);
    }
}
