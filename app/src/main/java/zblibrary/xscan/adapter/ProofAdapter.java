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

package zblibrary.xscan.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import zblibrary.xscan.model.Proof;
import zblibrary.xscan.model.User;
import zblibrary.xscan.view.ProofView;
import zblibrary.xscan.view.UserView;
import zuo.biao.library.base.BaseAdapter;

/**用户adapter
 * @author Lemon
 */
public class ProofAdapter extends BaseAdapter<Proof, ProofView> {
	//	private static final String TAG = "UserAdapter";

	public ProofAdapter(Activity context) {
		super(context);
	}

	@Override
	public ProofView createView(int position, ViewGroup parent) {
		return new ProofView(context, parent);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

}