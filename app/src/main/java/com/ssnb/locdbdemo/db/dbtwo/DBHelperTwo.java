package com.ssnb.locdbdemo.db.dbtwo;

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

public class DBHelperTwo extends AbsDBHelper{

    private static final String DB_NAME = "DB_TWO";
    private static final int DB_VERSION = 1;

    public DBHelperTwo(Context context) {
        super(context, DB_NAME, null,DB_VERSION);
    }

    @Override
    protected void initTabCreateSqls() {
        addTabCreateSql(SqlFactory.createTab(new TwoDBTabOneStructure()));
        addTabCreateSql(SqlFactory.createTab(new TwoDBTabTwoStructure()));
    }

    @Override
    protected void initUpgradeSql() {
    }
}
