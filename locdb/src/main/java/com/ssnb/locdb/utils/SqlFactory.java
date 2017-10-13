package com.ssnb.locdb.utils;

import android.support.annotation.NonNull;

import com.ssnb.locdb.bean.AbsTabStructure;
import com.ssnb.locdb.bean.FieldBean;
import com.ssnb.locdb.bean.FieldType;
import com.ssnb.locdb.exception.CreatesSqlException;

/**
 * ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
 * 作者：SSNB
 * 日期：2017/10/11
 * 邮箱：blq_ssnb@outlook.com
 * 修改次数：1
 * 描述：
 * sql 的生产工厂
 * ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
 */

public class SqlFactory {

    private static final String CREATE_TAB = " CREATE TABLE IF NOT EXISTS ";
    private static final String PRIMARY_KEY = " PRIMARY KEY ";
    private static final String AUTO_INCREMENT = " AUTOINCREMENT ";
    private static final String NOT_NULL = " NOT NULL ";
    private static final String DEFAULT = " DEFAULT ";
    private static final String UNIQUE = " UNIQUE ";
    private static final String LEFT_BRACKETS = " ( ";
    private static final String RIGHT_BRACKETS = " ) ";

    public static String createTab(@NonNull String tableName, @NonNull AbsTabStructure tabStructure){
        StringBuilder createSql = new StringBuilder();
        if(tableName == null || tableName.trim().isEmpty()){
            throw new CreatesSqlException("sql tableName can not be null or empty");
        }
        createSql.append(CREATE_TAB).append(tableName).append(LEFT_BRACKETS);
        for (FieldBean bean : tabStructure.getFieldList()){
            createSql.append(bean.getName()).append(bean.getType().getStyle());
            if(bean.isPrimaryKey()){
                createSql.append(PRIMARY_KEY);
                if(bean.isAutoincrement()&&bean.getType()== FieldType.INTEGER){
                    createSql.append(AUTO_INCREMENT);
                }
            }
             /*添加唯一字段*/
            if(bean.isUnique()){
                createSql.append(UNIQUE);
            }

            if(!bean.isNullable()){
                createSql.append(NOT_NULL);
            }
            if(!bean.getDefaultValue().trim().isEmpty()){
                createSql.append(DEFAULT).append(bean.getDefaultValue());
            }
            createSql.append(",");
        }
        if(tabStructure.getFieldList().size() !=0){
            createSql.deleteCharAt(createSql.length()-1);
        }
        createSql.append(RIGHT_BRACKETS);

        return createSql.toString();
    }

    public static String createTab(@NonNull AbsTabStructure tabStructure){
        return createTab(tabStructure.getFieldList().get(0).getName()/*.getTableName()*/,tabStructure);
    }

}
