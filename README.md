# GTool 

[![](https://jitpack.io/v/Goorwl/GTool.svg)](https://jitpack.io/#Goorwl/GTool)

## Introduction	
   
This programe is a set of Android development which contains a class library of common utils and some development tips.This program can help you improve your development efficiency about Android.

## How To Config

Step 1: In project `build.gradle` file, add following content:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }		// add this line
		}
	}

Step 2:In module `build.gradle` file, add following content:

	dependencies {
		implementation 'com.github.Goorwl:GTool:0.0.2'   // add this line,please keep lastest
	}

## How To Use

### LogUtils

	// todo

### ThreadUtils

	// todo

### SPUtils

	// todo

### SingleTimer

	// todo

### SingleInstance

	// todo

### LiveEventBus

Refer : [JeremyLiao](https://github.com/JeremyLiao/LiveEventBus)

## Other Tips 

[TIPS](./TIPS.md)

## ProGuard

In project `proguard-rules.pro` file ,add following content：

	-keep class com.goorwl.utils.** { *; }
	-keepclassmembers class com.goorwl.utils.** { *; }
	-dontwarn com.goorwl.utils.**

## License

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
