package com.ssnb.locdb.bean;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作者: Blq_s
 * 日期: 2017/9/29
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 每个表结构对象，在初始化的时候需要初始化表结构
 * ================================================
 */

public abstract class AbsTabStructure {
    private List<FieldBean> list;
    public AbsTabStructure(){
        list = new ArrayList<>();
        initFileBean();
    }

    /**
     * 初始化表结构对象
     */
    protected abstract void initFileBean();

    /**
     * 添加表字段
     * @param bean 字段对象
     */
    protected void addFileBean(FieldBean bean){
        list.add(bean);
    }

    /**
     * 获取表字段对象
     * @return 表字段列表
     */
    public List<FieldBean> getFieldList(){
        return list;
    }

    @NonNull
    public abstract String getTableName();
}
