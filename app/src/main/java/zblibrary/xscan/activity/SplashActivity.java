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

import zblibrary.xscan.R;
import zblibrary.xscan.application.DemoApplication;
import zblibrary.xscan.model.User;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import org.w3c.dom.Text;

/**闪屏activity，保证点击桌面应用图标后无延时响应
 * @author Lemon
 */
public class SplashActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
//				startActivity(AboutActivity.createIntent(SplashActivity.this));

				User user = DemoApplication.getInstance().getCurrentUser();
				if (null != user && user.getUid() > 0 && !TextUtils.isEmpty(user.getToken())) {
					startActivity(MainTabActivity.createIntent(SplashActivity.this));
				} else {
					startActivity(LoginActivity.createIntent(SplashActivity.this));
				}
				finish();
			}
		}, 500);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.fade, R.anim.hold);
	}

}