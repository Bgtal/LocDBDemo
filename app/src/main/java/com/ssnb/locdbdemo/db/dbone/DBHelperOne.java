package com.ssnb.locdbdemo.db.dbone;

import android.content.Context;

import com.ssnb.locdb.AbsDBHelper;
import com.ssnb.locdb.utils.SqlFactory;

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

public class DBHelperOne extends AbsDBHelper{
    private static final String DB_NAME = "DB_ONE";
    private static final int DB_VERSION = 1;

    public DBHelperOne(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    protected void initTabCreateSqls() {
        addTabCreateSql(SqlFactory.createTab(new OneDBTabOneStructure()));
        addTabCreateSql(SqlFactory.createTab(new OneDBTabTwoStructure()));

    }

    @Override
    protected void initUpgradeSql() {

    }
}
