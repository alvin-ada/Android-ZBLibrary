/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package zblibrary.xscan.model;

import zblibrary.xscan.util.AppUtils;
import zuo.biao.library.base.BaseModel;

/**
 * 用户类
 *
 * @author alvin
 */
public class Proof extends BaseModel {

    private static final long serialVersionUID = 1L;

    public final static int RESULT_UNCONFIRMED = 0;
    public final static int RESULT_TRUE = 1;
    public final static int RESULT_FALSE = 2;


    private int result; //0.尚未结果, 1.真. 2.假
    private long create_at; //"created_at": 1557021811


    /**
     * 默认构造方法，JSON等解析时必须要有
     */
    public Proof() {
        //default
    }

    public Proof(long id) {
        this();
        this.id = id;
    }

    public Proof(long id, int result, int create_at) {
        this(id);
        this.result = result;
        this.create_at = create_at;
    }


    /**
     * 以下getter和setter可以自动生成
     * <br>  eclipse: 右键菜单 > Source > Generate Getters and Setters
     * <br>  android studio: 右键菜单 > Generate > Getter and Setter
     */
    public String parseResult() {
        String ret;
        switch (result) {
            case 1:
                ret = "真";
                break;
            case 2:
                ret = "假";
                break;
            default:
                ret = "未确认";
                break;
        }
        return ret;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getCreateAtStr() {
        return AppUtils.getDateTimeStr(create_at, "yyyy-MM-dd HH:mm:ss");
    }

    public long getCreateAt() {
        return create_at;
    }

    public void setCreateAt(long create_at) {
        this.create_at = create_at;
    }


    @Override
    protected boolean isCorrect() {//根据自己的需求决定，也可以直接 return true
        return create_at > 0;// && StringUtil.isNotEmpty(phone, true);
    }

}
