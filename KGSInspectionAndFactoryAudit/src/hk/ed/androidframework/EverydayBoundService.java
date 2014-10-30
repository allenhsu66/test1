/**
 * Copyright (c) 2011 Delicious Computer(r)
 * 
 * Permission is hereby granted to Social Media Broadcasts and associated
 * documentation files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify,
 * merge, publish and with strict prohibition on any distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 * @author Yeung Hoi Hang <chrisyeung@deliciouscomputer.com>
 * @copyright 2011 Deilicious Computer
 * @version 1
 * 
 */
package hk.ed.androidframework;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;

/**
 * 
 */
public abstract class EverydayBoundService extends IntentService {
	public EverydayBoundService(String name) {
		super(name);
	}

	// bouncing service framwork:
	private WeakReference<Activity>	activityRef;

	public static <S extends EverydayBoundService> BoundServiceConnection<S> bind(Activity owner, Class<S> serviceClass,
			final IConnection<S> ic) {
		BoundServiceConnection<S> conn = new BoundServiceConnection<S>(owner, serviceClass) {

			@Override
			public void onServiceConnected(S service) {
				ic.onServiceConnected(service);
			}
		};
		Intent i = new Intent(owner, serviceClass);
		owner.bindService(i, conn, BIND_AUTO_CREATE);
		return conn;
	}

	public static <S extends EverydayBoundService> void unbind(Activity owner, BoundServiceConnection<S> conn) {
		conn.unbind();
		if (owner != null && conn != null)
			owner.unbindService(conn);
	}

	private static class BoundServiceBinder<S extends EverydayBoundService> extends Binder {
		private final S	service;

		public BoundServiceBinder(S service) {
			this.service = service;
		}

		S getService() {
			return service;
		}

	}

	public interface IConnection<S extends EverydayBoundService> {
		void onServiceConnected(S service);
	}

	public abstract static class BoundServiceConnection<S extends EverydayBoundService> implements ServiceConnection,
			IConnection<S> {

		private final Activity				owner;
		private BoundServiceBinder<S>	mBinder;

		public BoundServiceConnection(Activity owner, Class<S> serviceClass) {
			this.owner = owner;
		}

		@SuppressWarnings("unchecked")
		@Override
		public final void onServiceConnected(ComponentName name, IBinder service) {
			mBinder = (BoundServiceBinder<S>) service;
			mBinder.getService().registerActivity(owner);
			onServiceConnected(mBinder.getService());
		}

		@Override
		public final void onServiceDisconnected(ComponentName name) {
			mBinder.getService().unregisterActivity(owner);
		}

		void unbind() {
			if (owner != null && mBinder != null && mBinder.getService() != null)
				mBinder.getService().unregisterActivity(owner);
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public IBinder onBind(Intent intent) {
		return new BoundServiceBinder(this);
	}

	public void registerActivity(Activity activity) {
		activityRef = new WeakReference<Activity>(activity);
		onActivityBinded(activity);
	}

	public void unregisterActivity(Activity owner) {
		onActivityUnbinded(owner);
		if (activityRef != null) {
			Activity w = activityRef.get();
			if (w == owner) {
				activityRef = null;
			}
		}
	}

	protected void onActivityBinded(Activity activity) {
	}

	protected void onActivityUnbinded(Activity owner) {
	}

	protected Activity getActivity() {
		if (activityRef == null) {
			return null;
		} else {
			return activityRef.get();
		}
	}
}
