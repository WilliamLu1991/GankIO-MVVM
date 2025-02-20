package com.williamlu.widgetlib.pickerview;

import android.content.Context;
import android.view.View;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.contrarywind.interfaces.IPickerViewData;
import com.williamlu.widgetlib.R;

import java.util.ArrayList;

/**
 * @Author: WilliamLu
 * @Data: 2018/12/29
 * @Description:自定义联动控制器
 */
public class CustomPickerView {
    private static CustomPickerView instance;

    private CustomPickerView() {
    }

    public static CustomPickerView getInstance() {
        if (instance == null) {
            synchronized (CustomPickerView.class) {
                if (instance == null) {
                    instance = new CustomPickerView();
                }
            }
        }
        return instance;
    }

    public interface OnPvOptionsSelectListener {
        void onOptionsSelect(int options1, int options2, int options3, View v);
    }

    public OptionsPickerView showPickerView(Context context, String title, ArrayList<IPickerViewData> list,
                                            ArrayList<ArrayList<String>> list2,
                                            ArrayList<ArrayList<ArrayList<String>>> list3,
                                            final CustomPickerView.OnPvOptionsSelectListener listener) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                listener.onOptionsSelect(options1, options2, options3, v);
            }
        })
                .setTitleText(title)
                .setSubmitColor(context.getResources().getColor(R.color.color_02af66))
                .setCancelColor(context.getResources().getColor(R.color.color_02af66))
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(list, list2, list3);
        return pvOptions;
    }

    public OptionsPickerView showPickerView(Context context, String title, ArrayList<String> list,
                                            final CustomPickerView.OnPvOptionsSelectListener listener) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                listener.onOptionsSelect(options1, options2, options3, v);
            }
        })
                .setTitleText(title)
                .setSubmitColor(context.getResources().getColor(R.color.color_02af66))
                .setCancelColor(context.getResources().getColor(R.color.color_02af66))
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(list);
        return pvOptions;
    }
}
