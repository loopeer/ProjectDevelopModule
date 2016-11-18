package com.loopeer.databindpack.validator;

import android.databinding.BaseObservable;

import java.io.Serializable;
import java.lang.reflect.Field;

public class ObservableModel extends BaseObservable implements Serializable {

    @Override public String toString() {
        Class<?> clazz = getClass();
        Field fields[] = clazz.getDeclaredFields();

        StringBuilder sb = new StringBuilder(clazz.getName());
        sb.append("{");

        int len = fields.length;
        for (int index=0; index < len; index++) {
            try {
                Field field = fields[index];
                if (field.get(this) == null) continue;
                sb.append(field.getName()).append("=").append(field.get(this).toString());
                if (index < len - 1) {
                    sb.append(", ");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        sb.append("}");
        return sb.toString();
    }
}
