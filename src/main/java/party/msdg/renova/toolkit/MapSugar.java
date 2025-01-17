package party.msdg.renova.toolkit;

import java.util.HashMap;


/** 
 * map语法糖
 * @author syy
 * @since 2014-11-18 下午4:09:57 
 * @version 1.0
 */
public class MapSugar {

    /** 
     * 封装string类型的map
     * @return  hashMap
     */
    public static HashMap<String, String> stringMap(Object...params) {
        HashMap<String, String> map = new HashMap<>(params.length / 2);
        for (int i=1; i<params.length; i=i+2) {
            map.put(String.valueOf(params[i-1]), String.valueOf(params[i]));
        }
        return map;
    }

    /**
     * 封装string类型的map，参数更加明确
     * @return  hashMap
     */
    public static HashMap<String, String> stringMap(String...params) {
        HashMap<String, String> map = new HashMap<>(params.length / 2);
        for (int i=1; i<params.length; i=i+2) {
            map.put(params[i-1], params[i]);
        }
        return map;
    }

    /**
     * 封装string类型的map
     * @return  hashMap
     */
    public static HashMap<String, Object> paramMap(Object...params) {
        HashMap<String, Object> map = new HashMap<>(params.length/2);
        for (int i=1; i<params.length; i=i+2) {
            map.put(String.valueOf(params[i-1]), params[i]);
        }
        return map;
    }

    /**
     * 封装object类型的map
     * @return  hashMap
     */
    public static HashMap<Object, Object> objectMap(Object...params) {
        HashMap<Object, Object> map = new HashMap<>(params.length / 2);
        for (int i=1; i<params.length; i=i+2) {
            map.put(params[i-1], params[i]);
        }
        return map;
    }
}
