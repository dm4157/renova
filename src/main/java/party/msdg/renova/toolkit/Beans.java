package party.msdg.renova.toolkit;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bean复制工具类，支持不同类型间的深度复制
 */
public class Beans {
    
    // 缓存字段信息，提高性能
    private static final Map<Class<?>, List<Field>> FIELD_CACHE = new ConcurrentHashMap<>();
    
    // 已经处理过的对象引用，防止循环引用导致的无限递归
    private static final ThreadLocal<Map<Object, Object>> REFERENCES = 
        ThreadLocal.withInitial(HashMap::new);
    
    /**
     * 复制对象到指定类型的对象，支持深度复制
     * @param source 源对象
     * @param targetClass 目标对象类型
     * @param <T> 目标对象类型
     * @return 复制后的对象
     */
    public static <T> T copy(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        
        try {
            // 清空当前线程的引用缓存
            REFERENCES.get().clear();
            return targetClass.cast(copyObject(source, targetClass));
        } catch (Exception e) {
            throw new RuntimeException("复制对象失败: " + e.getMessage(), e);
        } finally {
            // 清理引用缓存
            REFERENCES.get().clear();
        }
    }
    
    /**
     * 递归复制对象
     * @param source 源对象
     * @param targetClass 目标类型
     * @return 复制后的对象
     */
    private static Object copyObject(Object source, Class<?> targetClass) throws Exception {
        if (source == null) {
            return null;
        }
        
        Class<?> sourceClass = source.getClass();
        
        // 处理基本类型和不可变类型
        if (isImmutable(sourceClass)) {
            return source;
        }
        
        // 检查是否已经处理过该对象（处理循环引用）
        Map<Object, Object> references = REFERENCES.get();
        if (references.containsKey(source)) {
            return references.get(source);
        }
        
        // 处理数组
        if (sourceClass.isArray()) {
            return copyArray(source, targetClass);
        }
        
        // 处理集合类型
        if (source instanceof Collection) {
            return copyCollection((Collection<?>) source, targetClass);
        }
        
        if (source instanceof Map) {
            return copyMap((Map<?, ?>) source, targetClass);
        }
        
        // 处理普通对象
        return copyBean(source, targetClass);
    }
    
    /**
     * 复制数组
     */
    private static Object copyArray(Object array, Class<?> targetClass) throws Exception {
        int length = Array.getLength(array);
        Class<?> componentType = targetClass.getComponentType();
        Object newArray = Array.newInstance(componentType, length);
        REFERENCES.get().put(array, newArray);
        
        for (int i = 0; i < length; i++) {
            Object element = Array.get(array, i);
            Object copiedElement = copyObject(element, componentType);
            Array.set(newArray, i, copiedElement);
        }
        
        return newArray;
    }
    
    /**
     * 复制集合
     */
    private static Collection<?> copyCollection(Collection<?> source, Class<?> targetClass) throws Exception {
        Collection<Object> newCollection;
        
        if (targetClass.isInterface()) {
            if (List.class.isAssignableFrom(targetClass)) {
                newCollection = new ArrayList<>(source.size());
            } else if (Set.class.isAssignableFrom(targetClass)) {
                newCollection = new HashSet<>(source.size());
            } else if (Queue.class.isAssignableFrom(targetClass)) {
                newCollection = new LinkedList<>();
            } else {
                newCollection = new ArrayList<>(source.size());
            }
        } else {
            @SuppressWarnings("unchecked")
            Collection<Object> instance = (Collection<Object>) targetClass.newInstance();
            newCollection = instance;
        }
        
        REFERENCES.get().put(source, newCollection);
        
        // 获取泛型参数类型（如果可能）
        Class<?> elementType = Object.class;
        // 简化处理，实际项目中可以使用更复杂的泛型解析
        
        for (Object item : source) {
            Object copiedItem = copyObject(item, elementType);
            newCollection.add(copiedItem);
        }
        
        return newCollection;
    }
    
    /**
     * 复制Map
     */
    private static Map<?, ?> copyMap(Map<?, ?> source, Class<?> targetClass) throws Exception {
        Map<Object, Object> newMap;
        
        if (targetClass.isInterface()) {
            if (SortedMap.class.isAssignableFrom(targetClass)) {
                newMap = new TreeMap<>();
            } else if (LinkedHashMap.class.isAssignableFrom(targetClass)) {
                newMap = new LinkedHashMap<>();
            } else {
                newMap = new HashMap<>();
            }
        } else {
            @SuppressWarnings("unchecked")
            Map<Object, Object> instance = (Map<Object, Object>) targetClass.newInstance();
            newMap = instance;
        }
        
        REFERENCES.get().put(source, newMap);
        
        // 获取泛型参数类型（如果可能）
        Class<?> keyType = Object.class;
        Class<?> valueType = Object.class;
        // 简化处理，实际项目中可以使用更复杂的泛型解析
        
        for (Map.Entry<?, ?> entry : source.entrySet()) {
            Object key = copyObject(entry.getKey(), keyType);
            Object value = copyObject(entry.getValue(), valueType);
            newMap.put(key, value);
        }
        
        return newMap;
    }
    
    /**
     * 复制普通Bean对象
     */
    private static Object copyBean(Object source, Class<?> targetClass) throws Exception {
        if (targetClass.isInterface() || Modifier.isAbstract(targetClass.getModifiers())) {
            throw new IllegalArgumentException("目标类型不能是接口或抽象类: " + targetClass.getName());
        }
        
        Object target = targetClass.newInstance();
        REFERENCES.get().put(source, target);
        
        // 获取源对象和目标对象的字段
        List<Field> sourceFields = getFieldList(source.getClass());
        List<Field> targetFields = getFieldList(targetClass);
        
        // 创建目标字段映射，便于快速查找
        Map<String, Field> targetFieldMap = new HashMap<>();
        for (Field field : targetFields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                targetFieldMap.put(field.getName(), field);
            }
        }
        
        // 复制字段值
        for (Field sourceField : sourceFields) {
            if (Modifier.isStatic(sourceField.getModifiers())) {
                continue;
            }
            
            String fieldName = sourceField.getName();
            Field targetField = targetFieldMap.get(fieldName);
            
            if (targetField != null && isAssignable(sourceField.getType(), targetField.getType())) {
                sourceField.setAccessible(true);
                targetField.setAccessible(true);
                
                Object value = sourceField.get(source);
                Class<?> targetFieldType = targetField.getType();
                Object copiedValue = copyObject(value, targetFieldType);
                targetField.set(target, copiedValue);
            }
        }
        
        return target;
    }
    
    /**
     * 判断源类型是否可以赋值给目标类型
     */
    private static boolean isAssignable(Class<?> sourceType, Class<?> targetType) {
        return targetType.isAssignableFrom(sourceType) || 
               (sourceType.isPrimitive() && wrapperType(sourceType).equals(targetType)) ||
               (targetType.isPrimitive() && wrapperType(targetType).equals(sourceType));
    }
    
    /**
     * 获取基本类型的包装类型
     */
    private static Class<?> wrapperType(Class<?> primitiveType) {
        if (primitiveType == int.class) return Integer.class;
        if (primitiveType == long.class) return Long.class;
        if (primitiveType == double.class) return Double.class;
        if (primitiveType == float.class) return Float.class;
        if (primitiveType == boolean.class) return Boolean.class;
        if (primitiveType == byte.class) return Byte.class;
        if (primitiveType == char.class) return Character.class;
        if (primitiveType == short.class) return Short.class;
        return primitiveType;
    }
    
    /**
     * 获取类的所有字段（包括父类字段）
     */
    private static List<Field> getFieldList(Class<?> clazz) {
        return FIELD_CACHE.computeIfAbsent(clazz, c -> {
            List<Field> fields = new ArrayList<>();
            while (c != null && c != Object.class) {
                Collections.addAll(fields, c.getDeclaredFields());
                c = c.getSuperclass();
            }
            return fields;
        });
    }
    
    /**
     * 判断是否为不可变类型
     */
    private static boolean isImmutable(Class<?> clazz) {
        return clazz.isPrimitive() ||
               clazz == String.class ||
               clazz == Boolean.class ||
               clazz == Character.class ||
               clazz == Byte.class ||
               clazz == Short.class ||
               clazz == Integer.class ||
               clazz == Long.class ||
               clazz == Float.class ||
               clazz == Double.class ||
               clazz.isEnum() ||
               clazz == Class.class;
    }
}
