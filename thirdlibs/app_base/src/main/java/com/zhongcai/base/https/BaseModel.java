package com.zhongcai.base.https;

/**
 * Created by zc3 on 2018/3/22.
 */

public class BaseModel<T> {

    public class MetaModel{
        private int code;
        private String msg;
    }

    public class DataModel{
        private int subCode;
        private String subMsg;
        private T result;

        public int getSubCode() {
            return subCode;
        }

        public String getSubMsg() {
            return subMsg;
        }

        public T getResult() {
            return result;
        }
    }

    private MetaModel meta;
    private DataModel data;

    public MetaModel getMeta() {
        return meta;
    }

    public void setMeta(MetaModel meta) {
        this.meta = meta;
    }

    public DataModel getData() {
        return data;
    }

    public void setData(DataModel data) {
        this.data = data;
    }
}

