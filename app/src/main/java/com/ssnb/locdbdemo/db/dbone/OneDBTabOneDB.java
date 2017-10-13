package com.ssnb.locdbdemo.db.dbone;

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

public class OneDBTabOneDB {

    private DBOperator dbOperator;
    public OneDBTabOneDB (Context context){
        dbOperator = DBOperatorManager.singletion().getOperator(new DBHelperOne(context));
    }

    public void add(String name,int age){
        String sql = "INSERT INTO " + OneDBTabOneStructure.TAB_NAME
                +" ( "
                + OneDBTabOneStructure.NAME+","
                + OneDBTabOneStructure.AGE+")"
                +" Values(?,?)";
        dbOperator.operator(sql,new Object[]{name,age});
    }

    public void delete(){
        String sql = " DELETE FROM " + OneDBTabOneStructure.TAB_NAME
                + "  WHERE( "
                +OneDBTabOneStructure.ONE_ID + " = "
                + "(SELECT " + OneDBTabOneStructure.ONE_ID + " FROM " + OneDBTabOneStructure.TAB_NAME + " LIMIT 1)"
                +")";
        dbOperator.operator(sql);

    }

    public void update(String name, int age ){
        String sql = " UPDATE " + OneDBTabOneStructure.TAB_NAME
                + " SET "
                + OneDBTabOneStructure.NAME + " =? ,"
                + OneDBTabOneStructure.AGE + " =? ";
        dbOperator.operator(sql,new Object[]{name,age});
    }

    public String select(){
        String sql = "SELECT * FROM " + OneDBTabOneStructure.TAB_NAME ;
        List<Map<String,String>> datas = dbOperator.query(sql,new String[]{OneDBTabOneStructure.ONE_ID,OneDBTabOneStructure.NAME,OneDBTabOneStructure.AGE});
        return datas.toString();
    }
}
