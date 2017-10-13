package com.ssnb.locdbdemo.db.dbone;

import com.ssnb.locdb.bean.AbsTabStructure;
import com.ssnb.locdb.bean.FieldBean;
import com.ssnb.locdb.bean.FieldType;

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

public class OneDBTabTwoStructure extends AbsTabStructure{
    public static final String TAB_NAME = "one_tab_two";

    public static final String ONE_ID = "two_id";
    public static final String NAME = "twoname";
    public static final String AGE = "twoage";

    @Override
    protected void initFileBean() {

        addFileBean(new FieldBean.Builder(ONE_ID, FieldType.INTEGER).setPrimaryKey(true,true).build());
        addFileBean(new FieldBean.Builder(NAME,FieldType.TEXT).build());
        addFileBean(new FieldBean.Builder(AGE,FieldType.INTEGER).build());
    }

    @Override
    public String getTableName() {
        return TAB_NAME;
    }

}
