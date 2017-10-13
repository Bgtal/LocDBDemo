package com.ssnb.locdbdemo.db.dbtwo;

import android.content.Context;
import android.util.Log;

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

public class TwoDBTabOneDB {
    private DBOperator dbOperator;
    public TwoDBTabOneDB (Context context){
        dbOperator = DBOperatorManager.singletion().getOperator(new DBHelperTwo(context));
    }

    public void add(String name,int age){
        String sql = "INSERT INTO " + TwoDBTabOneStructure.TAB_NAME
                +" ( "
                + TwoDBTabOneStructure.NAME+","
                + TwoDBTabOneStructure.AGE+")"
                +" Values(?,?)";
        dbOperator.operator(sql,new Object[]{name,age});
    }

    public void delete(){
        String sql = " DELETE FROM " + TwoDBTabOneStructure.TAB_NAME
                + "  WHERE( "
                +TwoDBTabOneStructure.ONE_ID + " = "
                + "(SELECT " + TwoDBTabOneStructure.ONE_ID + " FROM " + TwoDBTabOneStructure.TAB_NAME + " LIMIT 1)"
                +")";
        dbOperator.operator(sql);
    }

    public void update(String name, int age ){
        String sql = " UPDATE " + TwoDBTabOneStructure.TAB_NAME
                + " SET "
                + TwoDBTabOneStructure.NAME + " =? ,"
                + TwoDBTabOneStructure.AGE + " =? ";
        dbOperator.operator(sql,new Object[]{name,age});
    }

    public String select(){
        String sql = "SELECT * FROM " + TwoDBTabOneStructure.TAB_NAME;
        List<Map<String,String>> datas = dbOperator.query(sql,new String[]{TwoDBTabOneStructure.ONE_ID,TwoDBTabOneStructure.NAME,TwoDBTabOneStructure.AGE});
        return datas.toString();
    }
}
