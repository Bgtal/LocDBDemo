package com.ssnb.locdb.exception;

import android.database.sqlite.SQLiteException;

/**
 * ================================================
 * 作者: Blq_s
 * 日期: 2017/9/29
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 字段对象错误
 * ================================================
 */

public class FieldBeanException extends SQLiteException{

    public FieldBeanException() {
    }

    public FieldBeanException(String error) {
        super(error);
    }

    public FieldBeanException(String error, Throwable cause) {
        super(error, cause);
    }
}
