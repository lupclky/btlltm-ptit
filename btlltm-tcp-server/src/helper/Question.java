/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author admin
 */
public class Question {
    public static String renQuestion() {
        List<Integer> listAnswer = new ArrayList<>();
        int t=((int)(Math.random()*3));
                
        if (t==0){
            listAnswer.add(1);
            listAnswer.add(2);
            listAnswer.add(3);
            Collections.shuffle(listAnswer);
        }else 
        if (t==1){
            listAnswer.add(2);
            listAnswer.add(1);
            listAnswer.add(3);
            Collections.shuffle(listAnswer);
        }
        else if(t==2){
            listAnswer.add(3);
            listAnswer.add(2);
            listAnswer.add(1);
            Collections.shuffle(listAnswer);
        }

        String msg = "";
        msg = msg + "1;2;";
        
        for(Integer x: listAnswer) {
//            System.out.println("dap an " + i + " : " + x);
    
            msg += x + ";";
        }
        
//        System.out.println(msg);
        return msg;
    }
    

    
//    public static void main(String[] args) {
//        renQuestion();
//    }
}
