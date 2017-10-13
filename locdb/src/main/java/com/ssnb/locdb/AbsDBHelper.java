package com.ssnb.locdb;

import android.content.Context;
import android.util.Log;

import com.tencent.wcdb.database.SQLiteDatabase;
import com.tencent.wcdb.database.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * ================================================
 * 作者: Blq_s
 * 日期: 2017/9/29
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 添加描述
 * ================================================
 */

public abstract class AbsDBHelper extends SQLiteOpenHelper {

    private LinkedList<String> createSqls;
    private HashMap<Integer,List<String>> upgradeSql;

    public AbsDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("AbsDBHelper", "创建数据库:"+getDatabaseName());
        createSqls = new LinkedList<>();
        initTabCreateSqls();
        for (String tabCreateSql : createSqls){
            db.execSQL(tabCreateSql);
        }
    }

    /**
     * 初始化创建表的sql
     * 只有在{@link #onCreate(SQLiteDatabase)} 的时候会调用到
     */
    protected abstract void initTabCreateSqls();

    /**
     * 在初始化建表语句的时候调用该方法加入到列表中
     * @param sql 建表语句
     */
    protected void addTabCreateSql(String sql){
        if(createSqls == null){
            createSqls = new LinkedList<>();
        }
        createSqls.add(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        upgradeSql = new HashMap<>();
        initUpgradeSql();

        /*对版本进行排序*/
        ArrayList<Integer> versions = new ArrayList<>(upgradeSql.keySet());
        Collections.sort(versions, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });

        /*便利版本*/
        for (Integer version: versions){
            /*如果当前升级版本大于老版本，那么就升级*/
            if(version>=oldVersion){
                for (String sql : upgradeSql.get(version)){
                    db.execSQL(sql);
                }
            }
        }
    }

    /**
     * 初始化升级语句
     */
    protected abstract void initUpgradeSql();

    /**
     * 添加升级语句
     * @param upgradeVersion 升级版本
     * @param sql 升级语句
     */
    protected void addUpgradeSql(int upgradeVersion,String sql){
        if(upgradeSql == null){
            upgradeSql = new HashMap<>();
        }
        /*获取map中对应版本的升级语句列表*/
        List<String> sqls = upgradeSql.get(upgradeVersion);
        /*如果语句列表为空->该版本未添加升级语句*/
        if(sqls == null){
            /*创建列表*/
            sqls = new LinkedList<>();
            /*加入map中*/
            upgradeSql.put(upgradeVersion,sqls);
        }
        /*添加语句*/
        sqls.add(sql);
    }


}
