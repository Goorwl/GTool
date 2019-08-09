# Viewpager2

## 统一布局（加载view）

    viewPager2.setAdapter(new RecyclerView.Adapter<TvViewHolder>() {
        @NonNull
        @Override
        public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(mcontext).inflate(R.layout.vp_layout, parent, false);
            return new TvViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
            holder.tvTest.setText("测试数据" + position);
        }

        @Override
        public int getItemCount() {
            return 5;
        }
    });


	class TvViewHolder extends RecyclerView.ViewHolder {
	
	    TextView tvTest;

	    TvViewHolder(@NonNull View itemView) {
	        super(itemView);
	        tvTest = itemView.findViewById(R.id.vp_tv);
	    }
	}

## 加载不同Fragment（加载fragment）
	
	viewPager2.setAdapter(new FragmentStateAdapter(this) {
	    @NonNull
	    @Override
	    public Fragment createFragment(int position) {
	        Fragment fragment;
	        switch (position) {
	            case 0:
	                fragment = new Frag0();
	                break;
	            case 1:
	                fragment = new Frag1();
	                break;
	            case 2:
	                fragment = new Frag2();
	                break;
	            default:
	                fragment = new FragDef();
	                break;
	        }
	        return fragment;
	    }
	
	    @Override
	    public int getItemCount() {
	        return 4;
	    }
	});

## 自动轮播

在Activity内：

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (true) {  // 自动滑动
                int currentItem = viewPager2.getCurrentItem();
                if (currentItem == list.size - 1) {		// 最后一个索引
                    currentItem = 0;
                } else {
                    currentItem++;
                }
                if (currentItem == 0) {
                    viewPager2.setCurrentItem(currentItem, false);		// 第二个参数不要过渡动画
                } else {
                    viewPager2.setCurrentItem(currentItem);
                }
                mHandler.postDelayed(runnable, 2000);
            }
        }
    };

## 和TabLayout 绑定

build 添加 ：

    implementation 'com.google.android.material:material:1.1.0-alpha09'

使用方式：

    new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.OnConfigureTabCallback() {
        @Override
        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
            tab.select();
        }
    }).attach();

