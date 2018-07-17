package com.thinkgem.jeesite.modules.sco;

import com.thinkgem.jeesite.modules.sco.util.AmountUtil;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Created by jeesite on 2015/11/18.
 */
public class TestMain {

    public static void main(String[] args) {
//        String s = "3,";
//        System.out.println(s.split(",")[0]);
//        MathContext mc = new MathContext(4, RoundingMode.HALF_DOWN);
//        System.out.println(new BigDecimal(80.0080).divide(new BigDecimal(101.0000),mc));
        String ch = AmountUtil.digitUppercase(3209);
//        String ch = AmountUtil.digitUppercase(1101);
        System.out.println(ch);
    }
}
