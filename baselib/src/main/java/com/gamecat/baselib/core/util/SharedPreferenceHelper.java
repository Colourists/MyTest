package com.gamecat.baselib.core.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceHelper {
	public static final String RESERVED_PREFERENCE_DEVICE = "sys_a";

	private String mName;
	private SharedPreferences mSharedPreferences;

	public SharedPreferenceHelper(Context context, String name) {
		mName = name;
		if (mName == null) {
			throw new RuntimeException("name can not be null");
		}
		mSharedPreferences = context.getSharedPreferences(mName, Context.MODE_PRIVATE);
	}

	public String getPreferenceFileName() {
		return mName;
	}

	@SuppressLint("NewApi")
	private void commit(Editor editor) {
		if (SystemVersionUtil.hasGingerbread()) {
			editor.apply();
		} else {
			editor.commit();
		}
	}

	public synchronized void put(String key, String value) {
		Editor editor = mSharedPreferences.edit();
		editor.putString(key, value);

		commit(editor);
	}

	public void put(String key, int value) {
		Editor editor = mSharedPreferences.edit();
		editor.putInt(key, value);
		commit(editor);
	}

	public void put(String key, long value) {
		Editor editor = mSharedPreferences.edit();
		editor.putLong(key, value);

		commit(editor);
	}

	public void putFloat(String key, float value) {
		Editor editor = mSharedPreferences.edit();
		editor.putFloat(key, value);

		commit(editor);
	}

	public void putBoolean(String key, boolean value) {
		Editor editor = mSharedPreferences.edit();
		editor.putBoolean(key, value);

		commit(editor);
	}

	public void remove(String key) {
		Editor editor = mSharedPreferences.edit();
		editor.remove(key);

		commit(editor);
	}

	public String get(String key, String defValue) {
		try {
			return mSharedPreferences.getString(key, defValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defValue;
	}

	public String get(String key) {
		try {
			return mSharedPreferences.getString(key, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getInt(String key, int defValue) {
		try {
			return mSharedPreferences.getInt(key, defValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defValue;
	}

	public long getLong(String key, long defValue) {
		try {
			return mSharedPreferences.getLong(key, defValue);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return defValue;
	}

	public float getFloat(String key, float defValue) {
		try {
			return mSharedPreferences.getFloat(key, defValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defValue;
	}

	public boolean getBoolean(String key, boolean defValue) {
		try {
			return mSharedPreferences.getBoolean(key, defValue);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return defValue;
	}
}
