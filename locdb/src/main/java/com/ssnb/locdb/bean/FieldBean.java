package com.ssnb.locdb.bean;

import android.support.annotation.NonNull;

import com.ssnb.locdb.exception.FieldBeanException;

/**
 * ================================================
 * 作者: Blq_s
 * 日期: 2017/9/29
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 表字段对象，用{@link Builder} 来实例化
 * ================================================
 */

public final class FieldBean {
    /**
     * 字段名称
     */
    private String name;
    /**
     * 字段类型
     */
    private FieldType type;
    /**
     * 默认值
     */
    private String defaultValue = "";

    /**
     * 是可以为空
     */
    private boolean isNullable = true;
    /**
     * 是否为主键
     */
    private boolean isPrimaryKey = false;

    /**
     * 是否自增长
     */
    private boolean isAutoincrement = false;
    /**
     * 是否唯一
     */
    private boolean isUnique = false;

    private FieldBean(Builder builder){
        setName(builder.name);
        setType(builder.type);
        setDefaultValue(builder.defaultValue);
        setNullable(builder.isNullable);
        setPrimaryKey(builder.isPrimaryKey);
        setAutoincrement(builder.isAutoincrement);
        setUnique(builder.isUnique);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        if(name == null|| name.trim().isEmpty()){
            throw new FieldBeanException("FieldName is null");
        }
        this.name = name;
    }

    public FieldType getType() {
        return type;
    }

    private void setType(FieldType type) {
        if(type == null){
            throw new FieldBeanException("FieldType is null");
        }
        this.type = type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    private void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isNullable() {
        return isNullable;
    }

    private void setNullable(boolean nullable) {
        this.isNullable = nullable;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    private void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public boolean isAutoincrement() {
        return isAutoincrement;
    }

    private void setAutoincrement(boolean autoincrement) {
        isAutoincrement = autoincrement;
    }

    public boolean isUnique() {
        return isUnique;
    }

    private void setUnique(boolean unique) {
        isUnique = unique;
    }

    public static class Builder{
        /**
         * 字段名称
         */
        private String name;
        /**
         * 字段类型
         */
        private FieldType type;
        /**
         * 默认值
         */
        private String defaultValue = "";

        /**
         * 是可以为空
         */
        private boolean isNullable = true;
        /**
         * 是否为主键
         */
        private boolean isPrimaryKey = false;

        /**
         * 是否自增长
         */
        private boolean isAutoincrement = false;
        /**
         * 是否唯一
         */
        private boolean isUnique = false;

        public Builder(@NonNull String name, @NonNull FieldType type){
            this.name = name;
            this.type = type;
        }

        public Builder setName(@NonNull String name) {
            this.name = name;
            return this;
        }

        public Builder setType(@NonNull FieldType type) {
            this.type = type;
            return this;
        }

        public Builder setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public Builder setNullable(boolean nullable) {
            this.isNullable = nullable;
            return this;
        }

        public Builder setPrimaryKey(boolean primaryKey,boolean autoincrement) {
            this.isPrimaryKey = primaryKey;
            this.isAutoincrement = autoincrement;
            return this;
        }
        public Builder setUnique(boolean unique) {
            this.isUnique = unique;
            return this;
        }

        public FieldBean build(){
            return new FieldBean(this);
        }
    }
}
