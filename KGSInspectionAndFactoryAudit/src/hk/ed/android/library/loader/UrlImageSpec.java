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

import android.view.animation.Animation;
import android.widget.ImageView.ScaleType;

/**
 * The specification for loading a url image.
 */
public class UrlImageSpec {
	boolean reflectionSupport = false;
	boolean cacheSupport = true;
	ScaleType scaleType = null;
	// private int loadingIconId = android.R.drawable.stat_notify_sync;
	// private int noImageIconId = android.R.drawable.stat_notify_error;
	private int loadingIconId = 0;
	private int noImageIconId = 0;
	ScaleType noImageIconScaleType = ScaleType.CENTER_INSIDE;
	private Animation onLoadAnimation;

	private long lastModifiedDate = 0;

	/**
	 * Default {@code reflectionSupport} = false, {@code cacheSupport}=true,
	 * {@code scaleType}=null, no loading image and error image.
	 */
	public UrlImageSpec() {}

	public int getLoadingIconId() {
		return loadingIconId;
	}

	public int getNoImageIconId() {
		return noImageIconId;
	}

	public ScaleType getScaleType() {
		return scaleType;
	}

	public boolean isCacheSupport() {
		return cacheSupport;
	}

	public boolean isReflectionSupport() {
		return reflectionSupport;
	}

	/**
	 * Set the localer to support caching
	 * 
	 * @param cacheSupport
	 */
	public void setCacheSupport(boolean cacheSupport) {
		this.cacheSupport = cacheSupport;
	}

	/**
	 * Set the icon id when the picture is loading
	 * 
	 * @param loadingIconId
	 */
	public void setLoadingIconId(int loadingIconId) {
		this.loadingIconId = loadingIconId;
	}

	/**
	 * Set the icon id if the picture cannot be laoded
	 * 
	 * @param noImageIconId
	 */
	public void setNoImageIconId(int noImageIconId) {
		this.noImageIconId = noImageIconId;
	}

	/**
	 * Set to make the laoder to generate reflection effect
	 * 
	 * @param reflectionSupport
	 */
	public void setReflectionSupport(boolean reflectionSupport) {
		this.reflectionSupport = reflectionSupport;
	}

	/**
	 * Set the {@link ScaleType} of of the image view to be loaded
	 * 
	 * @param scaleType
	 */
	public void setScaleType(ScaleType scaleType) {
		this.scaleType = scaleType;
	}

	public Animation getOnLoadAnimation() {
		return onLoadAnimation;
	}

	public void setOnLoadAnimation(Animation onLoadAnimation) {
		this.onLoadAnimation = onLoadAnimation;
	}

	public ScaleType getNoImageIconScaleType() {
		return noImageIconScaleType;
	}

	public void setNoImageIconScaleType(ScaleType noImageIconScaleType) {
		this.noImageIconScaleType = noImageIconScaleType;
	}

	public long getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(long lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}
