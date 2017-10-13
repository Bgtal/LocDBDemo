package com.ssnb.locdb.bean;

/**
 * ================================================
 * 作者: Blq_s
 * 日期: 2017/9/29
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 字段类型 INTEGER 和 TEXT
 * ================================================
 */

public enum  FieldType {
    INTEGER(" INTEGER "),
    TEXT(" TEXT ");
    String style;
    FieldType(String style){
        this.style = style;
    }

    public String getStyle() {
        return style;
    }
    public String getStyleTrim(){
        return style.trim();
    }
}
