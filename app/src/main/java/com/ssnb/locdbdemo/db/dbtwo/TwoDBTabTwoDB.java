package com.ssnb.locdbdemo.db.dbtwo;

import android.content.Context;

import com.ssnb.locdb.DBOperator;
import com.ssnb.locdb.DBOperatorManager;

import java.util.List;
import java.util.Map;

/**
 * ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
 * 作者：SSNB
 * 日期：2017/10/12
 * 邮箱：blq_ssnb@outlook.com
 * 修改次数：1
 * 描述：
 * <p>
 * ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
 */

public class TwoDBTabTwoDB {
    private DBOperator dbOperator;
    public TwoDBTabTwoDB (Context context){
        dbOperator = DBOperatorManager.singletion().getOperator(new DBHelperTwo(context));
    }

    public void add(String name,int age){
        String sql = "INSERT INTO " + TwoDBTabTwoStructure.TAB_NAME
                +" ( "
                + TwoDBTabTwoStructure.NAME+","
                + TwoDBTabTwoStructure.AGE+")"
                +" Values(?,?)";
        dbOperator.operator(sql,new Object[]{name,age});
    }

    public void delete(){
        String sql = " DELETE FROM " + TwoDBTabTwoStructure.TAB_NAME
                + "  WHERE( "
                +TwoDBTabTwoStructure.ONE_ID + " = "
                + "(SELECT " + TwoDBTabTwoStructure.ONE_ID + " FROM " + TwoDBTabTwoStructure.TAB_NAME + " LIMIT 1)"
                +")";
        dbOperator.operator(sql);

    }

    public void update(String name, int age ){
        String sql = " UPDATE " + TwoDBTabTwoStructure.TAB_NAME
                + " SET "
                + TwoDBTabTwoStructure.NAME + " =? ,"
                + TwoDBTabTwoStructure.AGE + " =? ";
        dbOperator.operator(sql,new Object[]{name,age});
    }

    public String select(){
        String sql = "SELECT * FROM " + TwoDBTabTwoStructure.TAB_NAME;
        List<Map<String,String>> datas = dbOperator.query(sql,new String[]{TwoDBTabTwoStructure.ONE_ID,TwoDBTabTwoStructure.NAME,TwoDBTabTwoStructure.AGE});
        return datas.toString();
    }
}
