# 代码片段

## 像素转换

    public static int dpToPx(Context ctx, int dp) {
        DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToDp(Context ctx, int px) {
        DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

## 版本信息

	// 获取版本名称
    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }
	// 获取版本号
    public static int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

## 隐藏（透明）状态栏

	private void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);        	//  最近任务白屏显示，界面不允许截屏、录屏
		setContentView(R.layout.activity_qukuan);
		// TODO：
	}

## 双击返回

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - firstClick > 2000) {
                firstClick = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    System.exit(0);
                    android.os.Process.killProcess(android.os.Process.myPid());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        return false;
    }

## 防止多次点击

	public class BtnUtils {
	    private static Map<String, Long> records = new HashMap<>();
	    public static boolean isFastDoubleClick() {
	        if (records.size() > 800) {
	            records.clear();
	        }
	        //本方法被调用的文件名和行号作为标记
	        StackTraceElement ste = new Throwable().getStackTrace()[1];
	        String key = ste.getFileName() + ste.getLineNumber();
	        Long lastClickTime = records.get(key);
	        long thisClickTime = System.currentTimeMillis();
	        records.put(key, thisClickTime);
	        if (lastClickTime == null) {
	            lastClickTime = 0L;
	        }
	        long timeDuration = thisClickTime - lastClickTime;
	        return 0 < timeDuration && timeDuration < 1500;		// 1500ms
	    }
	}

	btn.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                if (Utils.isFastDoubleClick()) {
	                    // 多次点击的逻辑操作
	                }
	            }
	        });

## 强制更新对话框不消失

    AlertDialog alertDialog = new AlertDialog.Builder(mContext)
            .setTitle("版本更新")
            .setMessage("发现新版本 " + versionInfoBean.getData().getNewVersion() + " , 需要更新才能继续使用")
            .setPositiveButton("去更新", null)
            .setCancelable(false)
            .create();
    alertDialog.show();
    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.warning_text));
    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
			// TODO
        }
    });

## 键盘隐藏

	InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	//隐藏软键盘 //够用~
	imm.hideSoftInputFromWindow(et_edit.getWindowToken(), 0);
	//显示软键盘
	imm.showSoftInputFromInputMethod(tv.getWindowToken(), 0); 
	 //切换软键盘的显示与隐藏  
	imm.toggleSoftInputFromWindow(tv.getWindowToken(), 0, InputMethodManager.HIDE_NOT_ALWAYS);
	
## .gitnore规则
	
	#built application files
	*.apk
	*.ap_
	
	# files for the dex VM
	*.dex
	
	# Java class files
	*.class
	
	# generated files
	bin/
	gen/
	
	# Local configuration file (sdk path, etc)
	local.properties
	
	# Windows thumbnail db
	Thumbs.db
	
	# OSX files
	.DS_Store
	
	# Android Studio
	*.iml
	.idea
	#.idea/workspace.xml - remove # and delete .idea if it better suit your needs.
	.gradle
	build/
	.navigation
	captures/
	output.json 
	
	#NDK
	obj/
	.externalNativeBuild

## Glide 加载布局背景方式

	SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
	    @Override
	    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
	        mAppBarLayout.setBackground(resource);
	    }
	};
	Glide.with(mContext).load(body.getAweme_list().get(0).getAuthor().getAvatar_larger()).into(simpleTarget);

## 输入框限制输入两位小数

	//android:inputType="numberDecimal"
	mEt.setFilters(new InputFilter[]{new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (source.equals(".") && dest.toString().length() == 0) {
                return "0.";
            }
            if (dest.toString().contains(".")) {
                int index = dest.toString().indexOf(".");
                int mlength = dest.toString().substring(index).length();
                if (mlength == 3) {
                    return "";
                }
            }
            return null;
        }
    }});