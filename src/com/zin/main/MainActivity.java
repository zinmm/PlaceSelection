package com.zin.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ListView mCityl, mDistrict, mProvince;

	private DBManager dbm;
	private SQLiteDatabase db;

	private cityAdapter adapter;
	private cityAdapter adapter1;
	private cityAdapter adapter2;
	
	private SpinnerItem item;

	private List<SpinnerItem> provinces;
	private List<SpinnerItem> countrys;
	private List<SpinnerItem> city;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		item = new SpinnerItem();

		mCityl = (ListView) findViewById(R.id.cityl);
		mDistrict = (ListView) findViewById(R.id.district);
		mProvince = (ListView) findViewById(R.id.province);

		adapter = new cityAdapter(getApplicationContext());
		mCityl.setAdapter(adapter);

		adapter1 = new cityAdapter(getApplicationContext());
		mDistrict.setAdapter(adapter1);

		adapter2 = new cityAdapter(getApplicationContext());
		mProvince.setAdapter(adapter2);

		initProvince();
	}

	public void initProvince() {
		adapter.clear();
		dbm = new DBManager(MainActivity.this);
		dbm.openDatabase();
		db = dbm.getDatabase();
		provinces = new ArrayList<SpinnerItem>();
		try {
			String sql = "select * from fs_province";
			Cursor cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				String code = cursor.getString(cursor
						.getColumnIndex("ProvinceID"));
				String name = cursor.getString(cursor
						.getColumnIndex("ProvinceName")); 
				SpinnerItem myListItem = new SpinnerItem();
				myListItem.setName(name);
				myListItem.setPcode(code);
				myListItem.setcId(i);
				
				provinces.add(myListItem);
				cursor.moveToNext();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbm.closeDatabase();
			db.close();
		}

		for (int i = 0; i < provinces.size(); i++) {
			adapter.addItem(provinces.get(i).getName());
			adapter.notifyDataSetChanged();
		}

		mCityl.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String pcode = provinces.get(position).getPcode();
				
				initCity(pcode);
				initCountry(pcode);
			}
		});
	}

	public void initCity(String pcode) {
		dbm = new DBManager(this);
		dbm.openDatabase();
		db = dbm.getDatabase();
		city = new ArrayList<SpinnerItem>();

		try {
			String sql = "select * from fs_city where ProvinceID='" + pcode
					+ "'";
			Cursor cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				String code = cursor.getString(cursor.getColumnIndex("CityID"));
				String name = cursor.getString(cursor
						.getColumnIndex("CityName"));
				SpinnerItem myListItem = new SpinnerItem();
				myListItem.setName(name);
				myListItem.setPcode(code);
				city.add(myListItem);
				cursor.moveToNext();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		dbm.closeDatabase();
		db.close();
		
		adapter1.clear();
		for (int i = 0; i < city.size(); i++) {
			adapter1.addItem(city.get(i).getName());
			adapter1.notifyDataSetChanged();
		}

		mDistrict.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println(parent.getItemAtPosition(position).toString());
				String pcode = city.get(position).getPcode();
				initCountry(pcode);
			}
		});
	}

	public void initCountry(String pcode) {
		dbm = new DBManager(this);
		dbm.openDatabase();
		db = dbm.getDatabase();
		countrys = new ArrayList<SpinnerItem>();

		try {
			String sql = "select * from fs_district where CityID='" + pcode
					+ "'";
			Cursor cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				String code = cursor.getString(cursor
						.getColumnIndex("DistrictID"));
				String name = cursor.getString(cursor
						.getColumnIndex("DistrictName"));
				SpinnerItem myListItem = new SpinnerItem();
				myListItem.setName(name);
				myListItem.setPcode(code);
				countrys.add(myListItem);
				cursor.moveToNext();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		dbm.closeDatabase();
		db.close();
		
		adapter2.clear();
		for (int i = 0; i < countrys.size(); i++) {
			adapter2.addItem(countrys.get(i).getName());
			adapter2.notifyDataSetChanged();
		}

		mProvince.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(), countrys.get(position).getName(), Toast.LENGTH_SHORT).show();
			}
		});
	}

}
