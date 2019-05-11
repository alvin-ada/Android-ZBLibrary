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

package zblibrary.xscan.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import zblibrary.xscan.R;
import zblibrary.xscan.manager.DataManager;
import zblibrary.xscan.manager.OnHttpResponseListenerImpl;
import zblibrary.xscan.model.User;
import zblibrary.xscan.util.HttpRequest;
import zuo.biao.library.base.BaseModel;
import zuo.biao.library.interfaces.OnHttpResponseListener;
import zuo.biao.library.util.JSON;
import zuo.biao.library.util.Log;

public class LoginActivity extends Activity {

	private static final String TAG = "LoginActivity";






	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, LoginActivity.class);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);

		initView();
		initEvent();
		initData();
	}

//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	private EditText phoneNumEt;
	private TextView sendCodeTv;
	private EditText codeEt;
	private LinearLayout loginLl;

	public void initView() {//必须调用

		phoneNumEt = (EditText)findViewById(R.id.et_phone);
		sendCodeTv = (TextView)findViewById(R.id.tv_send_code);
		codeEt = (EditText)findViewById(R.id.et_code);
		loginLl = (LinearLayout)findViewById(R.id.ll_login_btn);
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	public void initData() {//必须调用
		String phoneNum = DataManager.getInstance().getCurrentUserPhone();
		if (!TextUtils.isEmpty(phoneNum)) {
			phoneNumEt.setText(phoneNum);
		}
	}


	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public void initEvent() {//必须调用
		sendCodeTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String phoneNum = phoneNumEt.getText().toString();
				if (checkPhone(phoneNum)) {
					HttpRequest.getCode(phoneNum, 1, new OnHttpResponseListener() {
						@Override
						public void onHttpResponse(int requestCode, String resultJson, Exception e) {
							Log.e(TAG, "code: " + resultJson);
						}
					});
				}
			}
		});

		loginLl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String phoneNum = phoneNumEt.getText().toString();
				String code = codeEt.getText().toString();
				if (checkPhone(phoneNum) && checkCode(code)) {
					HttpRequest.login(phoneNum, code, 2, new OnHttpResponseListener() {
						@Override
						public void onHttpResponse(int requestCode, String resultJson, Exception e) {
							Log.e(TAG, "login: " + resultJson);
							User user = null;
							try {//如果服务器返回的json一定在最外层有个data，可以用OnHttpResponseListenerImpl解析
								JSONObject jsonObject = JSON.parseObject(resultJson);
								JSONObject data = jsonObject == null ? null : jsonObject.getJSONObject("data");
								user = JSON.parseObject(data, User.class);
							} catch (Exception e1) {
								Log.e(TAG, "onHttpResponse  try { user = Json.parseObject(... >>" +
										" } catch (JSONException e1) {\n" + e1.getMessage());
							}

							if (BaseModel.isCorrect(user) == false && e != null) {
								Toast.makeText(LoginActivity.this, "登录异常，请稍后重试",
										Toast.LENGTH_SHORT).show();
							} else {
								user.setPhone(phoneNum);
								DataManager.getInstance().saveCurrentUser(user);
								startActivity(MainTabActivity.createIntent(LoginActivity.this));
							}
						}
					});
				}
			}
		});
	}


	//生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	protected void onDestroy() {
		super.onDestroy();


	}

	//生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>





	public boolean checkPhone(String phone) {
		String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
		if (TextUtils.isEmpty(phone) || phone.length() != 11) {
			Toast.makeText(this, "手机号应为11位数",
					Toast.LENGTH_SHORT).show();
			return false;
		} else {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(phone);
			boolean isMatch = m.matches();
			if (!isMatch) {
				Toast.makeText(this, "请填入正确的手机号",
						Toast.LENGTH_SHORT).show();
			}
			return isMatch;
		}
	}

	public boolean checkCode(String code) {
		if (TextUtils.isEmpty(code) || code.length() != 4) {
			Toast.makeText(this, "验证码应为4位数",
					Toast.LENGTH_SHORT).show();
			return false;
		}

		return true;
	}

	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}