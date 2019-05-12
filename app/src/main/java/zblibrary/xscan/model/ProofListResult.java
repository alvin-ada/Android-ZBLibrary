package zblibrary.xscan.model;

import zuo.biao.library.base.BaseModel;

public class ProofListResult extends BaseModel {

    public int code;
    public ProofData data;


    @Override
    protected boolean isCorrect() {
        return false;
    }
}
