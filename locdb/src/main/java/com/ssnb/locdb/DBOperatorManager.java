package com.ssnb.locdb;

import android.util.Log;

import java.util.HashMap;

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

public class DBOperatorManager {
    private static class SINGLETION{
        private static DBOperatorManager manager = new DBOperatorManager();
    }

    private HashMap<String,DBOperator> dbOperatorCache;

    private DBOperatorManager(){
        dbOperatorCache = new HashMap<>();
    }


    public static DBOperatorManager singletion(){
        return SINGLETION.manager;
    }

    public DBOperator getOperator(AbsDBHelper dbHelper){
        DBOperator operator = dbOperatorCache.get(dbHelper.getClass().getName());
        if(operator == null){
            operator = new DBOperator(dbHelper);
            dbOperatorCache.put(dbHelper.getClass().getName(),operator);
            Log.e("DBOperatorManager","new_dbOperator"+dbHelper.getClass().getName());
        }
        return operator;
    }
}
