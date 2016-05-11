package cn.cfanr.izhihudaily.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.cfanr.izhihudaily.model.NewsModel;

/**
 * @author xifan
 * @time 2016/5/4
 * @desc Json字符串解析工具类
 */
public class JsonTool {
    public static String getObjString(JSONObject jsonObject, String key){
        if(jsonObject==null){
            return "";
        }
        try {
            String value=jsonObject.getString(key);
            if(value==null){
                return "";
            }else{
                return value;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * json字符串转化为Map<String, String>（注意捕捉异常，防止不是json 格式的出现解析异常）
     */
    public static Map<String, String> jsonStr2Map(String jsonStr) {
        try {
            GsonBuilder gb = new GsonBuilder();
            Gson g = gb.create();
            Map<String, String> map = g.fromJson(jsonStr, new TypeToken<Map<String, String>>() {
            }.getType());
            return map;
        }catch(Exception e){
            return null;
        }
    }

    /**
     * Json字符串转化为Map<String, Object>
     */
    public static Map<String, Object> parseJson2Map(String jsonStr){
        JSONObject jsonObject ;
        try {
            jsonObject = new JSONObject(jsonStr);

            Iterator<String> keyIter= jsonObject.keys();
            String key;
            Object value ;
            Map<String, Object> valueMap = new HashMap<String, Object>();
            while (keyIter.hasNext()) {
                key = keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 安全获取值为Object类型的map的对应值
     * @param objectMap
     * @param key
     */
    public static String mapObjVal2Str(Map<String, Object> objectMap, String key){
        if(objectMap==null){
            return "";
        }
        Object obj=objectMap.get(key);
        if(obj==null){  //防止null.toString()异常
            return "";
        }
        return obj.toString();
    }

    /**
     * Json字符串转化为List-Map
     */
    public static List<Map<String, String>> parseJson2ListMap(String jsonStr){
        List<Map<String, String>> list = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            JSONObject jsonObj;
            list = new ArrayList<Map<String, String>>();
            for(int i = 0 ; i < jsonArray.length() ; i ++){
                jsonObj = (JSONObject)jsonArray.get(i);
                list.add(jsonStr2Map(jsonObj.toString()));
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 将JsonArray的字符串转化为List<String>
     */
    public static List<String> jsonArrayToList(String jsonStr){
        try{
            Gson gson=new Gson();
            Type type=new TypeToken<List<String>>(){
            }.getType();
            List<String> jsonBean=gson.fromJson(jsonStr, type);
            return jsonBean;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static List<NewsModel> jsonToNewsModelList(String jsonStr){
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<List<NewsModel>>() {
            }.getType();
            List<NewsModel> jsonBean = gson.fromJson(jsonStr, type);
            return jsonBean;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
