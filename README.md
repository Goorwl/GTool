# GTool 

[![](https://jitpack.io/v/Goorwl/GTool.svg)](https://jitpack.io/#Goorwl/GTool) [![HitCount](http://hits.dwyl.io/goorwl/GTool.svg)](http://hits.dwyl.io/goorwl/GTool) [![Build Status](https://travis-ci.org/Goorwl/GTool.svg?branch=master)](https://travis-ci.org/Goorwl/GTool) [![](https://img.shields.io/badge/API-19%2B-blue.svg)](https://img.shields.io/badge/API-19%2B-blue.svg) [![](https://img.shields.io/badge/Telegram-GOORWL-yellowgreen.svg)](https://t.me/goorwl) [![](https://img.shields.io/badge/blog-%E6%A9%99%E5%AD%90-blue.svg)](https://xiaozhuanlan.com/goorwl?rel=goorwl) [![](https://img.shields.io/badge/E--mail-goorwl%40163.com-pink.svg)](https://mailto:goorwl@163.com)

## Introduction	
   
This programe is a set of Android development which contains a class library of common utils and some development tips.This program can help you improve your development efficiency about Android.

## How To Config

Step 1: In project `build.gradle` file, add following content:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }			// add this line
		}
	}

Step 2:In module `build.gradle` file, add following content:

	dependencies {
		...
		implementation 'com.github.Goorwl:GTool:$latest_version'   // add this line, please keep lastest
	}

## How To Use

### LogUtils

This tool is used to output some infomation during debug.

* Added version 
	
		0.1.0

* Init
	
		LogUtils.setEnable(BuildConfig.DEBUG);		// when we release the apk this log will auto close. Called before the first execution.

* Setting

		LogUtils.setLevel(1); 	// 	filter log level by this number,only output levels greater than this number.

* Usage

		LogUtils.v(TAG, "onCreate: ");		// LEVEL:1
        LogUtils.i(TAG, "onCreate: ");		// LEVEL:2
        LogUtils.d(TAG, "onCreate: ");		// LEVEL:3
        LogUtils.w(TAG, "onCreate: ");		// LEVEL:4
        LogUtils.e(TAG, "onCreate: ");		// LEVEL:5
	

### ThreadUtils

This tool encapsulates thread operations by thread pool.

* Added version 
	
		0.1.0

* Init

		NONE

* Usage

	* on sub thread
	
	        ThreadUtils.runOnSubThread(()->{
	            // do something on sub thread
	        });

	* on main thread
	
	        ThreadUtils.runOnUiThread(()->{
	            // do something on main thread
	        });

### SPUtils

This tool encapsulates shareperference operations.

* Added version 
	
		0.1.0

* Init

		SPUtils.init(T extends Application);		// Called before the first execution.

* Usage

	* Int

		    boolean resPutInt = SPUtils.putInt(CONFIG_HOME, 1);
        	int resGetInt = SPUtils.getInt(CONFIG_HOME, 0);

	* String
		
			...
	* Float
	* Long
	* Boolen
	
### SingleTimer

This tool is used to count down globally.

* Added version 
	
		0.1.0

* Init

		NONE

* Usage

	* Start Timer
	
			SingleTimer.getInstance().startTime(5);		// Start a new timer regardless of whether it existed before. UNIT:SECONDS.
			SingleTimer.getInstance().setTime(3);		// Start a new timer with there is currently no timer, or ignore this setting.  UNIT:SECONDS.

	* Stop Timer

			SingleTimer.getInstance().stopTime();		// Stop the global timer. 

	* Get Timer
	
    	    int time = SingleTimer.getInstance().getTime();		// From the global timer get the rest of time.

### AppManager

* Added version 
	
		0.1.0

* Init

		NONE

* Usage

	* getTopActivity() : get the top activity of the stack activity.

		    CoreActivity topActivity = AppManager.getAppManager().getTopActivity();

	* getPreActivity() : get the pre activity of the activity which called this.

            CoreActivity preActivity = AppManager.getAppManager().getPreActivity(T extends CoreActivity);

	* backToHome() : back the main activity of this application.

			AppManager.getAppManager().backToHome();

	* backToTagFront() ：back the activity which last activity marked, don't include the marked activity.

			AppManager.getAppManager().backToTagFront();

	* backToTag() ：back the activity which activity marked, include the marked activity.

			AppManager.getAppManager().backToTag();

* NOTICE

	MUST USE WITH COREACTIVITY.

### RvAdapterWrapper

* Added version 
	
		0.1.1

* Init

		NONE

* Usage

	* addHeaderView(View view) : set the recyclerview head view

	        TextView textView = new TextView(context);
	        textView.setText("this is a head view");
	        adapterWrapper.addHeaderView(textView);

	* addFootView(View view) : set the recyclerview foot view
	
	        TextView textView = new TextView(context);
	        textView.setText("this is a foot view");
	        adapterWrapper.addFootView(textView);

	* setNormal(boolean isNormal) : set the view which you add if like normal view

			adapterWrapper.setNormal(true);

* NOTICE

	use this class wrap your own adataper


### CoreActivity

This tool is a base Activity which can be used instead of AppCompatActivity, and add some method to more effectiveness.

* Added version 
	
		0.1.0

* Init

		NONE

* Usage

	* jumpActivity(Class) : start activity.

			jumpActivity(MainActivity.class);

	* jumpActivity(Class, Bundle) : start activity with bundle.

			jumpActivity(MainActivity.class, bundle);

	* jumpActivity(String) : start activity with activity class name.

			jumpActivity("MainActivity");

	* setTag(String) : set tag for current activity.

			setTag("CONFIG_HOME");

	* getTag() : get tag from current activity.

			String tag = getTag();

### ImageUtils

* Added version 
	
		0.1.0

* Init 

		NONE

* Usage 

	* stringToBitmap(String) : get bitmap from base64 string of the image.
		
		    Bitmap xxx = ImageUtils.stringToBitmap("xxx");

	* bitmapToString(Bitmap) : get base64 string of the image from bitmap.
		
		    String res   = ImageUtils.bitmapToString(xxx);

	* bitmapToDrawable(Context, Bitmap) : Convert Bitmap to Drawable.
		
		    Drawable drawable = ImageUtils.bitmapToDrawable(mActivity, xxx);

	* drawableToBitmap(Drawable) : Convert Drawable to Bitmap.
		
		    Bitmap bitmap = ImageUtils.drawableToBitmap(drawable);

	* saveBitmap(ImageView, String) : Save image to local storage from ImageView.
		
		    ImageUtils.saveBitmap(imageView, "/sdcard/xxx.png);

### Loading

* Added version 
	
		1.0.0

* Init

		NONE

* Usage

	* getInstance(Context) : get an instance of AlertDialog with circle loading
		
    		AlertDialog instance = Loading.getInstance(mActivity);

	* show(Context) : show the dialog with activity

			Loading.show(mActivity);

	* dismiss() : dismiss the dialog and destory the alertdialog

			Loading.dismiss();


### LiveEventBus

Refer : [JeremyLiao](https://github.com/JeremyLiao/LiveEventBus)

## ViewPager2 Use

[ViewPager2](./ViewPager2.md)

## Other Tips 

[TIPS](./TIPS.md)

## Other View

### LoadingView 

![Loading](https://i.imgur.com/oLEHicT.gif)

[LoadingView](./VIEW/LoadingView.java)

## ProGuard

In project `proguard-rules.pro` file , add following content：

	-dontwarn com.goorwl.utils.**
	-keep class com.goorwl.utils.** { *; }
	-keepclassmembers class com.goorwl.utils.** { *; }
	-keep class androidx.lifecycle.** { *; }
	-keep class androidx.arch.core.** { *; }

## About Author

[橙子的Android小屋](https://xiaozhuanlan.com/goorwl?rel=goorwl)

## License

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
