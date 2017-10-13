package com.ssnb.locdb;

import android.database.Cursor;
import android.util.Log;

import com.tencent.wcdb.SQLException;
import com.tencent.wcdb.database.SQLiteDatabase;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ================================================
 * 作者: Blq_s
 * 日期: 2017/9/29
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * db操作工具
 * ================================================
 */

public final class DBOperator {
    private static final boolean DEBUG = true;
    private static final String TAG = "DBOperator";
    private static final String wcdb = "com.tencent.wcdb.database.SQLiteDatabase";

    private long waitTime = 5000L;
    private AbsDBHelper helper;
    private AtomicInteger dbOpenCount = new AtomicInteger(0);
    private long upTime = 0;
    private WaitRunnable waitRunnable;

    public DBOperator(AbsDBHelper dbHelper) {
        this.helper = dbHelper;
        waitRunnable = new WaitRunnable(this);
    }

    /**
     * 原子减1 等于0 就 close
     */
    private void close() {

        if (dbOpenCount.decrementAndGet() <= 0) {
            upTime = System.currentTimeMillis();
            if(!waitRunnable.isWaiting){
                new Thread(waitRunnable).start();
            }
            dbOpenCount.set(0);
//            helperClose();
        }
    }

    private void helperClose() {
        helper.close();
        Log.e(TAG, helper.getClass().getName() + "db close");
    }

    /**
     * 获取读取db
     *
     * @return
     */
    private SQLiteDatabase getReadableDatabase() {
        upTime = System.currentTimeMillis();
        if (dbOpenCount.incrementAndGet() <= 0) {
            dbOpenCount.set(1);
        }
        return helper.getReadableDatabase();
    }

    /**
     * 获取写入db
     *
     * @return
     */
    private SQLiteDatabase getWritableDatabase() {
        upTime = System.currentTimeMillis();
        if (dbOpenCount.incrementAndGet() <= 0) {
            dbOpenCount.set(1);
        }
        return helper.getWritableDatabase();
    }

    /**
     * 使用反射，获取执行影响的记录数
     * 该方法用于执行insert,delete,update操作
     */
    public int execute(String sql, Object[] params) {

        Class<?> classType;
        Method mth;

        int res = 0;
        try {
            classType = Class.forName(wcdb);
            mth = classType.getDeclaredMethod("executeSql", new Class[]{String.class, Object[].class});
            mth.setAccessible(true);

            SQLiteDatabase db = getWritableDatabase();

            res = (Integer) mth.invoke(db, sql, params);

        } catch (Exception e) {
            Log.e("catch-execute", e.getMessage());
        } finally {
            close();
        }

        return res;
    }

    /**
     * 用于添删改 sql 语句
     *
     * @param sql 完整的sql 语句
     */
    public void operator(String sql) {
        operator(sql, null);
    }

    /**
     * 用于添删改 sql 语句
     *
     * @param sql    sql 语句
     * @param params 填充的参数
     */
    public void operator(String sql, Object[] params) {
        synchronized (DBOperator.class) {
            SQLiteDatabase db = getWritableDatabase();
            try {
                if (params == null) {
                    db.execSQL(sql);
                } else {
                    db.execSQL(sql, params);
                }
            } catch (Exception e) {
                if (DEBUG) {
                    throw e;
                }
                Log.e(TAG, "catch-operator:" + e.getMessage());

            } finally {
                if (db != null) {
                    close();
                }
            }
        }

    }

    /**
     * 通过事物批量添删改
     *
     * @param sql        sql 语句
     * @param paramsList 批量添加的数据
     */
    public void batchOperator(String sql, List<Object[]> paramsList) {
        synchronized (DBOperator.class) {
            SQLiteDatabase db = getWritableDatabase();
            try {
                db.beginTransaction();
                for (Object[] params : paramsList) {
                    db.execSQL(sql, params);
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
                if (DEBUG) {
                    throw e;
                }
                Log.e(TAG, "catch-batchOperator:" + e.getMessage());
            } finally {
                if (db != null) {
                    db.endTransaction();
                    close();
                }
            }
        }
    }


    /**
     * 查询
     *
     * @param sql     查询语句
     * @param columns 需要返回的字段数组
     * @return 返回属性
     */
    public List<Map<String, String>> query(String sql, String[] columns) {
        return query(sql, null, columns);
    }

    /**
     * 查询
     *
     * @param sql     查询语句
     * @param params  查询参数
     * @param columns 需要返回的字段数组
     * @return 返回记录
     */
    public List<Map<String, String>> query(String sql, String[] params, String[] columns) {
        List<Map<String, String>> queryResult = new ArrayList<>();
        synchronized (DBOperator.class) {
            Cursor c = null;
            SQLiteDatabase db = getReadableDatabase();
            try {
                c = db.rawQuery(sql, params);
                if (c != null && c.getCount() > 0) {
                    while (c.moveToNext()) {
                        Map<String, String> data = new HashMap<>();
                        for (String column : columns) {
                            data.put(column, c.getString(c.getColumnIndex(column)));
                        }
                        queryResult.add(data);
                    }
                }
            } catch (Exception e) {
                if (DEBUG) {
                    throw e;
                }
                Log.e(TAG, "catch-query:" + e.getMessage());
            } finally {
                if (c != null) {
                    c.close();
                }
                if (db != null) {
                    close();
                }
            }
            return queryResult;
        }

    }


    /**
     * 判断某个字段是否为空
     *
     * @param sql    sql
     * @param params 参数
     * @param column field
     * @return result
     */
    public boolean isNull(String sql, String[] params, String column) {
        synchronized (DBOperator.class) {
            Cursor c = null;
            try {
                SQLiteDatabase db = getReadableDatabase();
                c = db.rawQuery(sql, params);
                if (c.moveToNext()) {
                    String msg = c.getString(0);
                    return msg == null || msg.trim().isEmpty() || msg.equalsIgnoreCase("null");
                }
            } catch (Exception e) {
                if (DEBUG) {
                    throw e;
                }
                Log.e(TAG, "catch-isNull:" + e.getMessage());
            } finally {
                if (c != null)
                    c.close();
                close();
            }
            return false;
        }
    }

    public int getCount(String sql, String[] params) {
        int count = 0;
        synchronized (DBOperator.class) {
            Cursor c = null;
            try {
                SQLiteDatabase db = getReadableDatabase();
                c = db.rawQuery(sql, params);

                count = c.getCount();

            } catch (Exception e) {
                if (DEBUG) {
                    throw e;
                }
                Log.e(TAG, "catch-getCount:" + e.getMessage());
            } finally {
                if (c != null)
                    c.close();
                close();
            }

            return count;
        }
    }


    private static final class WaitRunnable implements Runnable {
        WeakReference<DBOperator> weakReference;
        private boolean isWaiting = false;

        private WaitRunnable(DBOperator operator) {
            weakReference = new WeakReference<>(operator);
        }

        @Override
        public void run() {
            isWaiting = true;
            DBOperator operator;
            while (weakReference != null && weakReference.get() != null) {
                operator = weakReference.get();
                if (System.currentTimeMillis() - operator.upTime > operator.waitTime) {
                    operator.helperClose();
                    break;
                } else {
                    try {
                        Thread.sleep(operator.waitTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
            isWaiting = false;
        }
    }


}
