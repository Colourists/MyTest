package com.gamecat.baselib.core.addresspick;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bldby.baselibrary.R;
import com.bldby.baselibrary.app.GlobalUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddressPickerUtil {

    private static List<Province> provinceCityLists;
    private List<List<Province.CityListBean>> provinceCityLists1 = new ArrayList<>();
    private List<List<List<Province.AreaListBean>>> provinceCityLists2 = new ArrayList<>();

    public OptionsPickerView getOptionsPickerView(Activity context, boolean sanji, OnOptionsTextSelectListener onOptionsSelectListener) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                Province province = provinceCityLists.get(options1);
                Province.CityListBean cityListBean = provinceCityLists1.get(options1).get(options2);
                Province.AreaListBean areaListBean = provinceCityLists2.get(options1).get(options2).get(options3);
                onOptionsSelectListener.onOptionsSelect(province.name, cityListBean.name, areaListBean.name);
            }
        })
                .setSubmitText(context.getString(R.string.confirm))//确定按钮文字
                .setCancelText(context.getString(R.string.cancel))//取消按钮文字
                .setTitleText(context.getString(R.string.chooseAre))//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setTextColorCenter(Color.BLACK)
                .setTextColorOut(Color.BLACK)
                .setTitleBgColor(GlobalUtil.getCurrentActivity().getResources().getColor(R.color.white))//标题背景颜色 Night mode
                .setBgColor(GlobalUtil.getCurrentActivity().getResources().getColor(R.color.white))//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
//                .setLabels("省", "市", "区")//设置选择的三级单位
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(1, 1, 1)  //设置默认选中项
                .setOutSideCancelable(true)//点击外部dismiss default true
                .setDecorView(context.getWindow().getDecorView().findViewById(android.R.id.content))
                .isDialog(false)//是否显示为对话框样式
                .build();

        if (provinceCityLists == null) {
            try {
                String json = readString(GlobalUtil.getApplication().getAssets().open("region2.json"));
                Type type = new TypeToken<List<Province>>() {
                }.getType();
                provinceCityLists = new Gson().fromJson(json, type);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < provinceCityLists.size(); i++) {
            Province province = provinceCityLists.get(i);
            provinceCityLists1.add(province.cityList);
//                if (sanji) {
            List<List<Province.AreaListBean>> provinc = new ArrayList<>();
            for (int j = 0; j < province.getCitys().size(); j++) {
                provinc.add(province.cityList.get(j).areaList);
            }
            provinceCityLists2.add(provinc);

//                }
        }
        if (sanji) {
            //省市区
            pvOptions.setPicker(provinceCityLists, provinceCityLists1, provinceCityLists2);//添加数据源

        } else {
            // 省 市
            pvOptions.setPicker(provinceCityLists, provinceCityLists1);//添加数据源

        }
        return pvOptions;
    }

    private String readString(InputStream in) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringWriter sw = new StringWriter();
            String line;
            while ((line = br.readLine()) != null) {
                sw.write(line);
            }
            br.close();
            sw.close();
            return sw.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    public interface OnOptionsTextSelectListener {

        void onOptionsSelect(String options1, String options2, String options3);

    }
}
