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

package zblibrary.xscan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import zblibrary.xscan.R;
import zblibrary.xscan.activity.AboutActivity;
import zblibrary.xscan.activity.ProofListActivity;
import zblibrary.xscan.activity.SettingActivity;
import zblibrary.xscan.manager.DataManager;
import zblibrary.xscan.model.User;
import zblibrary.xscan.util.HttpRequest;
import zuo.biao.library.base.BaseFragment;
import zuo.biao.library.interfaces.OnHttpResponseListener;
import zuo.biao.library.ui.AlertDialog;
import zuo.biao.library.ui.AlertDialog.OnDialogButtonClickListener;
import zuo.biao.library.ui.EditTextInfoActivity;
import zuo.biao.library.util.Log;
import zuo.biao.library.util.StringUtil;

/**设置fragment
 * @author Lemon
 * @use new SettingFragment(),详细使用见.DemoFragmentActivity(initData方法内)
 */
public class SettingFragment extends BaseFragment implements OnClickListener, OnDialogButtonClickListener {
//	private static final String TAG = "SettingFragment";

	private final String TAG = "SettingFragment";

	//与Activity通信<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**创建一个Fragment实例
	 * @return
	 */
	public static SettingFragment createInstance() {
		return new SettingFragment();
	}

	//与Activity通信>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//类相关初始化，必须使用<<<<<<<<<<<<<<<<
		super.onCreateView(inflater, container, savedInstanceState);
		setContentView(R.layout.setting_fragment);
		//类相关初始化，必须使用>>>>>>>>>>>>>>>>

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

		return view;
	}



	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private ImageView ivSettingHead;
	private RelativeLayout mNameRL;
	private TextView mNickNameTV;
	private TextView mPhoneTV;
	@Override
	public void initView() {//必须调用

		ivSettingHead = findView(R.id.ivSettingHead);
		mNameRL = findView(R.id.llPersonName);
		mNickNameTV = findView(R.id.tvNickName);
		mPhoneTV = findView(R.id.tvPhone);
	}




	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initData() {//必须调用
		mNickNameTV.setText(DataManager.getInstance().getCurrentUserNickname());
		mPhoneTV.setText(DataManager.getInstance().getCurrentUserPhone());
	}

	private void logout() {
		context.finish();
	}


	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initEvent() {//必须调用
		mNameRL.setOnClickListener(this);
		findView(R.id.llPersonProofs).setOnClickListener(this);
	}




	@Override
	public void onDialogButtonClick(int requestCode, boolean isPositive) {
		if (! isPositive) {
			return;
		}

		switch (requestCode) {
		case 0:
			logout();
			break;
		default:
			break;
		}
	}



	@Override
	public void onClick(View v) {//直接调用不会显示v被点击效果
		switch (v.getId()) {
			case R.id.ivSettingHead:
//				showShortToast("onClick  ivSettingHead");
				break;
			case R.id.llPersonName:
				toActivity(EditTextInfoActivity.createIntent(context, EditTextInfoActivity.TYPE_NICK, "设置昵称", StringUtil.getTrimedString(mNickNameTV.getText())), 1, true);
				break;
			case R.id.llPersonProofs:
				toActivity(ProofListActivity.createIntent(context, ProofListActivity.RANGE_RECOMMEND));
				break;
			default:
				break;
		}
	}




	//生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1 && resultCode == RESULT_OK) {
			String result = data.getStringExtra(EditTextInfoActivity.RESULT_VALUE);
			mNickNameTV.setText(result);
			DataManager.getInstance().setCurrentUserNickname(result);

			//change name request
			HttpRequest.setNickname(result, 1, new OnHttpResponseListener() {
				@Override
				public void onHttpResponse(int requestCode, String resultJson, Exception e) {
					Log.e(TAG, "Set Nickname: " + resultJson);
				}
			});

		}
	}


	//生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}