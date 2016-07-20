package com.example.lenovo.myviews;

import java.io.File;
import java.util.ArrayList;

import views.Brand;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class BrandListActivity extends Activity implements OnItemClickListener,OnScrollListener{
	private TextView brand_headTextView;
	private ListView brandListView;
	private brandlistAdapter brandAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_brandlist);
		initDate();
		setviews();
	}

	/*
	 * 
	 * 获取views
	 */
	public void setviews() {
		TextView titleTextView = (TextView) findViewById(R.id.titleTextView);
		titleTextView.setText("选择品牌");
		brand_headTextView = (TextView) findViewById(R.id.brand_headTextView);
		brandListView = (ListView) findViewById(R.id.brandListView);
		brandListView.setAdapter(brandAdapter);
		brandListView.setOnScrollListener(this);
		brandListView.setOnItemClickListener(this);
		ImageView backtitleImageView=(ImageView) findViewById(R.id.titleIamgeView);
		backtitleImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BrandListActivity.this.finish();
			}
		});
	}

	/*
	 * 获取数据
	 */
	public void initDate() {
		ArrayList<Brand> brandlist = new ArrayList<Brand>();
		brandlist.add(new Brand("本田", "", "B"));
		brandlist.add(new Brand("本田", "", "B"));
		brandlist.add(new Brand("本田", "", "B"));
		brandlist.add(new Brand("本田", "", "B"));
		
		brandlist.add(new Brand("广汽", "", "G"));
		brandlist.add(new Brand("广汽", "", "G"));
		brandlist.add(new Brand("广汽", "", "G"));
		brandlist.add(new Brand("广汽", "", "G"));
		
		brandlist.add(new Brand("捷豹", "", "J"));
		brandlist.add(new Brand("捷豹", "", "J"));
		brandlist.add(new Brand("捷豹", "", "J"));
		brandlist.add(new Brand("捷豹", "", "J"));
		
		brandlist.add(new Brand("丰田", "", "F"));
		brandlist.add(new Brand("丰田", "", "F"));
		brandlist.add(new Brand("丰田", "", "F"));

		brandlist.add(new Brand("驰骋", "", "C"));
		brandlist.add(new Brand("驰骋", "", "C"));
		brandlist.add(new Brand("驰骋", "", "C"));
		brandlist.add(new Brand("驰骋", "", "C"));

		brandlist.add(new Brand("马萨拉蒂", "", "M"));
		brandlist.add(new Brand("马萨拉蒂", "", "M"));
		brandlist.add(new Brand("马萨拉蒂", "", "M"));
		brandlist.add(new Brand("马萨拉蒂", "", "M"));

		brandlist.add(new Brand("兰博基尼", "", "L"));
		brandlist.add(new Brand("兰博基尼", "", "L"));
		brandlist.add(new Brand("兰博基尼", "", "L"));
		brandlist.add(new Brand("兰博基尼", "", "L"));

		brandlist.add(new Brand("凯迪拉克", "", "K"));
		brandlist.add(new Brand("凯迪拉克", "", "K"));
		brandlist.add(new Brand("凯迪拉克", "", "K"));
		brandlist.add(new Brand("凯迪拉克", "", "K"));

		brandlist.add(new Brand("奇瑞", "", "Q"));
		brandlist.add(new Brand("奇瑞", "", "Q"));
		brandlist.add(new Brand("奇瑞", "", "Q"));
		brandlist.add(new Brand("奇瑞", "", "Q"));
		
		brandlist.add(new Brand("萨博", "", "S"));
		brandlist.add(new Brand("萨博", "", "S"));
		brandlist.add(new Brand("萨博", "", "S"));
		brandlist.add(new Brand("萨博", "", "S"));

		brandAdapter = new brandlistAdapter(this, brandlist);
	}

	/*
	 * 
	 * 品牌列表适配器
	 */
	private class brandlistAdapter extends BaseAdapter {
		private LayoutInflater lInflater;
		private ArrayList<Brand> brandlist;

		public brandlistAdapter(Context context, ArrayList<Brand> brandlist) {
			super();
			lInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.brandlist = brandlist;
		}

		@Override
		public int getCount() {
			return brandlist.size();
		}

		@Override
		public Object getItem(int position) {
			return brandlist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			viewholder holder;
			if (convertView == null) {
				convertView = lInflater.inflate(R.layout.lv_brand_item, parent,
						false);
				holder = new viewholder();
				holder.item_headTextView = (TextView) convertView
						.findViewById(R.id.brand_item_headTextView);
				holder.itemImageView = (ImageView) convertView
						.findViewById(R.id.brand_itemImageView);
				holder.itemTextView = (TextView) convertView
						.findViewById(R.id.brand_itemTextView);
				convertView.setTag(holder);
			} else {
				holder = (viewholder) convertView.getTag();
			}
			Brand brand = brandlist.get(position);
			holder.item_headTextView.setText(brand.getFrist_name());
			holder.itemTextView.setText(brand.getName());
			if (brand.getIconpath() != null && !"".equals(brand.getIconpath())
					&& (new File(brand.getIconpath())).exists()) {
				Bitmap bm = BitmapFactory.decodeFile(brand.getIconpath());
				holder.itemImageView.setImageBitmap(bm);
			}
			if(position==0||!(brandlist.get(position - 1).getFrist_name()
					.equals(brand.getFrist_name()))){
				holder.item_headTextView.setVisibility(View.VISIBLE);
			}else{
				holder.item_headTextView.setVisibility(View.INVISIBLE);
			}
			return convertView;
		}

		public class viewholder {
			TextView item_headTextView;
			ImageView itemImageView;
			TextView itemTextView;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String brand=((Brand) parent.getItemAtPosition(position)).getName();
		Intent intent=getIntent();
		intent.putExtra("brand", brand);
		setResult(RESULT_OK, intent);
		finish();
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if(view.getChildCount()==0)return;
		ViewGroup firstview=(ViewGroup) view.getChildAt(0);
		ViewGroup secondview=(ViewGroup) view.getChildAt(1);
		TextView firstTextView=(TextView) firstview.findViewById(R.id.brand_item_headTextView);
		TextView secondTextView=(TextView) secondview.findViewById(R.id.brand_item_headTextView);
		Brand firstBrand=(Brand) view.getItemAtPosition(firstVisibleItem);
		Brand secondBrand=(Brand) view.getItemAtPosition(firstVisibleItem+1);
		if(firstBrand.getFrist_name().equals(secondBrand.getFrist_name())){
			firstTextView.setVisibility(View.INVISIBLE);
			secondTextView.setVisibility(View.INVISIBLE);
			brand_headTextView.setText(firstBrand.getFrist_name());
			brand_headTextView.setVisibility(View.VISIBLE);
		}else{
			firstTextView.setVisibility(View.VISIBLE);
			secondTextView.setVisibility(View.VISIBLE);
			brand_headTextView.setVisibility(View.INVISIBLE);
		}
		
	}
}
