package com.pzl.dreamer.utils;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-28]
 */
public class XMLUtil {
    public static final String TAG = "XmlUtil";

    public static <T> T xml2Object(InputStream inputStream, Class<T> clazz) {
        XmlPullParser parser = Xml.newPullParser();
        Object object = null;
        List list = null;

        Class subClass = null;
        Object subObject = null;

        String tagName = "";
        try {
            parser.setInput(inputStream, "UTF-8");
            int eventType = parser.getEventType();

            while (eventType != 1) {
                switch (eventType) {
                    case 0:
                        object = clazz.newInstance();
                        break;
                    case 2:
                        String name = parser.getName();

                        if ("java.util.List".equals(tagName)) {
                            tagName = name;
                        }

                        Field[] f = null;
                        if (subClass == null) {
                            f = object.getClass().getDeclaredFields();

                            int count = parser.getAttributeCount();
                            for (int j = 0; j < count; j++)
                                setXmlValue(object, parser.getAttributeName(j), parser.getAttributeValue(j));
                        } else {
                            if (subObject == null) {
                                subObject = subClass.newInstance();
                            }
                            f = subObject.getClass().getDeclaredFields();
                        }

                        for (int i = 0; i < f.length; i++) {
                            if (!f[i].getName().equalsIgnoreCase(name))
                                continue;
                            if (f[i].getType().getName().equals("java.util.List")) {
                                tagName = "java.util.List";
                                Type type = f[i].getGenericType();
                                if ((type instanceof ParameterizedType)) {
                                    subClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
                                    subObject = subClass.newInstance();

                                    int count = parser.getAttributeCount();
                                    for (int j = 0; j < count; j++) {
                                        setXmlValue(subObject, parser.getAttributeName(j), parser.getAttributeValue(j));
                                    }
                                    if (list == null) {
                                        list = new ArrayList();
                                        f[i].setAccessible(true);
                                        f[i].set(object, list);
                                    }
                                }
                                break;
                            }

                            if (subObject != null) {
                                setXmlValue(subObject, name, parser.nextText());
                                break;
                            }

                            setXmlValue(object, name, parser.nextText());

                            break;
                        }

                        break;
                    case 3:
                        if ((subObject == null) || (!tagName.equalsIgnoreCase(parser.getName())))
                            break;
                        list.add(subObject);
                        subObject = null;
                    case 1:
                }

                eventType = parser.next();
            }

        } catch (Exception e) {
            Log.e("XmlUtil", e.getMessage());
        }
        return (T) object;
    }

    private static void setXmlValue(Object t, String name, String value) {
        try {
            Field[] f = t.getClass().getDeclaredFields();
            for (int i = 0; i < f.length; i++) {
                if (!f[i].getName().equalsIgnoreCase(name))
                    continue;
                f[i].setAccessible(true);

                Class fieldType = f[i].getType();

                if (fieldType == String.class) {
                    f[i].set(t, value);
                } else if (fieldType == Integer.TYPE) {
                    f[i].set(t, Integer.valueOf(Integer.parseInt(value)));
                } else if (fieldType == Float.TYPE) {
                    f[i].set(t, Float.valueOf(Float.parseFloat(value)));
                } else if (fieldType == Double.TYPE) {
                    f[i].set(t, Double.valueOf(Double.parseDouble(value)));
                } else if (fieldType == Long.TYPE) {
                    f[i].set(t, Long.valueOf(Long.parseLong(value)));
                } else if (fieldType == Short.TYPE) {
                    f[i].set(t, Short.valueOf(Short.parseShort(value)));
                } else if (fieldType == Boolean.TYPE) {
                    f[i].set(t, Boolean.valueOf(Boolean.parseBoolean(value)));
                } else {
                    f[i].set(t, value);
                }
            }

        } catch (Exception e) {
            Log.e("XmlUtil", e.getMessage());
        }
    }
}
