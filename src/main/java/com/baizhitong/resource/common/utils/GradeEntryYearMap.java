package com.baizhitong.resource.common.utils;

import java.util.Arrays;

import org.junit.Test;

public class GradeEntryYearMap {
        /**
         * 获取入学年份
         * ()<br>
         * @param gradeCode         年级
         * @param studyYearCode     入学年份
         * @return
         * @throws Exception
         */
    public static String getEntryYear(String gradeCode,String studyYearCode)throws Exception{
        /**
          * 100     学前      
          *  101    小班
          *  102    中班
          *  103    大班
          *  200    其他
          *  201    一年级
          *  202    二年级
          *  203    三年级
          *  204    四年级
          *  205    五年级
          *  206    六年级
          *  300    其他
          *  301    初一
          *  302    初二
          *  303    初三
          *  400    其他
          *  401    高一
          *  402    高二
          *  403    高三
         */

        String [][]grades={
                            {"100","101","102","103"},
                            {"201","202","203","204","205","206"},
                            {"301","302","303"},
                            {"401","402","403"}
                        };
        int index=0;
        for(String []grade:grades){
            index=Arrays.asList(grade).indexOf(gradeCode);
            if(index>=0)break;
        }
        if(index<0){
            throw new RuntimeException("没有找到年级信息"); 
        }
        //学年减去当前年级所在的位置恰好为班级的入学年份
        return (Integer.parseInt(studyYearCode)-index)+"";
    }
    
    @Test
    public void test()
    {
        try {
            System.out.println(getEntryYear("103","2016"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
