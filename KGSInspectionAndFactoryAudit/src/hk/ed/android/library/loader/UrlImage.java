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
package hk.ed.android.library.loader;

import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

import android.graphics.Bitmap;

/**
 * The data structure for image storing. Soft reference is used in this
 * implementation. The content for the image can only be accessed by the
 * {@link UrlImageLoader}
 */
public class UrlImage {
	public interface HashCallBack<V> extends Callable<V> {
		/**
		 * Make the hashcode for caching on the local device for abstract call
		 * back implement.
		 * 
		 * @return
		 */
		public String getHashCode();
	}

	URL url;
	final HashCallBack<URL> urlPreLoad;
	private SoftReference<Bitmap> ref = null;

	public UrlImage(HashCallBack<URL> urlPreLoad) {
		this.urlPreLoad = urlPreLoad;
		url = null;
	}

	/**
	 * Create a new url image.
	 * 
	 * @param link
	 *           the url of the picture.
	 * @throws MalformedURLException
	 */
	public UrlImage(String link) {
		try {
			url = new URL(link);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		urlPreLoad = null;
	}

	/**
	 * Create a new url image.
	 * 
	 * @param link
	 *           the url of the picture.
	 * 
	 */
	public UrlImage(URL url) {
		this.url = url;
		urlPreLoad = null;
	}

	public Bitmap getBitmap() {
		if (ref == null) {
			return null;
		}
		return ref.get();
	}

	/**
	 * Get the url for the picture.
	 * 
	 * @return
	 */
	public URL getUrl() {
		return url;
	}

	void setBitmap(Bitmap b) {
		ref = new SoftReference<Bitmap>(b);
	}
}
