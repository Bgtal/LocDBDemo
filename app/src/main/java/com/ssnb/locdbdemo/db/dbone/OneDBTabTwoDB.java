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

public class OneDBTabTwoDB {

    private DBOperator dbOperator;
    public OneDBTabTwoDB (Context context){
        dbOperator = DBOperatorManager.singletion().getOperator(new DBHelperOne(context));
    }

    public void add(String name,int age){
        String sql = "INSERT INTO " + OneDBTabTwoStructure.TAB_NAME
                +" ( "
                + OneDBTabTwoStructure.NAME+","
                + OneDBTabTwoStructure.AGE+")"
                +" Values(?,?)";
        dbOperator.operator(sql,new Object[]{name,age});
    }

    public void delete(){
        String sql = " DELETE FROM " + OneDBTabTwoStructure.TAB_NAME
                + "  WHERE( "
                +OneDBTabTwoStructure.ONE_ID + " = "
                + "(SELECT " + OneDBTabTwoStructure.ONE_ID + " FROM " + OneDBTabTwoStructure.TAB_NAME + " LIMIT 1)"
                +")";
        dbOperator.operator(sql);


    }

    public void update(String name, int age ){
        String sql = " UPDATE " + OneDBTabTwoStructure.TAB_NAME
                + " SET "
                + OneDBTabTwoStructure.NAME + " =? ,"
                + OneDBTabTwoStructure.AGE + " =? ";
        dbOperator.operator(sql,new Object[]{name,age});
    }

    public String select(){
        String sql = "SELECT * FROM " + OneDBTabTwoStructure.TAB_NAME;
        List<Map<String,String>> datas = dbOperator.query(sql,new String[]{OneDBTabTwoStructure.ONE_ID,OneDBTabTwoStructure.NAME,OneDBTabTwoStructure.AGE});
        return datas.toString();
    }
}
