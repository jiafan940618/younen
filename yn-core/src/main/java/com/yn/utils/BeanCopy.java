package com.yn.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import com.yn.domain.ISuperModel;


/**
 * Created by TT on 2017/1/20.
 */
public class BeanCopy extends BeanUtils {

	/**
	 * 同一个对象属性复制
	 * 
	 * @param source
	 * @param target
	 * @throws Exception
	 */
	public static void beanCopy(Object source, Object target) throws Exception {
		if (source == null || target == null) {
			throw new Exception("param is null.");
		}
		Field fields[] = source.getClass().getDeclaredFields();
		if (fields == null || fields.length == 0) {
			throw new Exception("Source bean no properties.");
		}
		for (Field field : fields) {
			field.setAccessible(true);
			Object o = field.get(source);
			if (o != null)
				field.set(target, o);
		}
	}

	public static void copyProperties(Object source, Object target) throws BeansException {
		try {
			copyProperties2(source, target, null, (String[]) null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void copyProperties2(Object source, Object target, Class<?> editable, String... ignoreProperties)
			throws Exception {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		if (editable != null) {
			if (!editable.isInstance(target)) {
				throw new IllegalArgumentException("Target class [" + target.getClass().getName()
						+ "] not assignable to Editable class [" + editable.getName() + "]");
			}
			actualEditable = editable;
		}
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

		for (PropertyDescriptor targetPd : targetPds) {
			Method targetWriteMethod = targetPd.getWriteMethod();
			String name = targetPd.getPropertyType().getName();
			if (!classNameEqList(name)) {
				if (targetWriteMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {

					// 获取set或者list 属性
					PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
					if (sourcePd != null) {
						// 获取get方法
						Method readMethod = sourcePd.getReadMethod();
						// 判断是否有gatset
						if (readMethod != null && ClassUtils.isAssignable(targetWriteMethod.getParameterTypes()[0],
								readMethod.getReturnType())) {
							try {
								//
								if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
									readMethod.setAccessible(true);
								}
								// 获取对象
								Object value = readMethod.invoke(source);
								if (!Modifier.isPublic(targetWriteMethod.getDeclaringClass().getModifiers())) {
									targetWriteMethod.setAccessible(true);
								}
								if (value != null) {
									targetWriteMethod.invoke(target, value);
								}
							} catch (Throwable ex) {
								throw new FatalBeanException(
										"Could not copy property '" + targetPd.getName() + "' from source to target",
										ex);
							}
						}
					}
				}
			} else {
				if (targetWriteMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
					PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
					if (sourcePd != null) {
						Method readMethod = sourcePd.getReadMethod();
						if (readMethod != null && ClassUtils.isAssignable(targetWriteMethod.getParameterTypes()[0],
								readMethod.getReturnType())) {
							if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}

							Object sourceValue = readMethod.invoke(source);
							// 获取targetValue
							PropertyDescriptor propertyDescriptor = getPropertyDescriptor(target.getClass(),
									sourcePd.getName());
							Object targetValue = propertyDescriptor.getReadMethod().invoke(target);
							
							// 转换类型
							Collection targetValueCollection1 = (Collection) targetValue;
							Collection sourceValueCollection2 = (Collection) sourceValue;

							if (StringUtil.isEmpty(sourceValue) && StringUtil.isEmpty(targetValue)) {
								continue;
							} else if (StringUtil.isEmpty(sourceValue) && !StringUtil.isEmpty(targetValue)) {
								// 需要删除数据库数据
								Collection targetDeleteElement = getDeleteElement(targetValueCollection1, sourceValueCollection2);
								targetValueCollection1.removeAll(targetDeleteElement);
								targetWriteMethod.invoke(target, targetValue);
								continue;
							} else if (!StringUtil.isEmpty(sourceValue) && StringUtil.isEmpty(targetValue)) {
								Object listCopy = listCopy(targetPd, sourceValue);
								targetWriteMethod.invoke(target, listCopy);
								continue;
							} else {
								// 需要删除数据库数据
								Collection targetDeleteElement = getDeleteElement(targetValueCollection1, sourceValueCollection2);
								targetValueCollection1.removeAll(targetDeleteElement);

								// 需要更新数据库数据
								Collection targetUpdateElement = getUpdateElement(targetValueCollection1, sourceValueCollection2);
								targetValueCollection1.addAll(targetUpdateElement);
								
								// 需要新增的元素
								Collection targetInsertElement = getInsertElement(targetValueCollection1, sourceValueCollection2);
								targetValueCollection1.addAll(targetInsertElement);
								
								targetWriteMethod.invoke(target, targetValue);

//								for (Object sourceValueCollection2object : sourceValueCollection2) {
//									if (sourceValueCollection2object instanceof ISuperModel) {
//
//										ISuperModel sourceiSuperModelobject2 = (ISuperModel) sourceValueCollection2object;
//										Long sourceid2 = sourceiSuperModelobject2.getId();
//										if (sourceid2 == null) {
//											// 將sourceid2放入target集合
//											targetValueCollection1.add(sourceValueCollection2object);
//										} else {
//
//											boolean flag = false;
//											for (Object targetValueCollectionObj : targetValueCollection1) {
//												// //如果target元素是从target集合中复制过来的就跳过
//												// if
//												// (sourceValueCollection2.contains(targetValueCollectionObj))
//												// {
//												// continue;
//												// }
//												if (targetValueCollectionObj instanceof ISuperModel) {
//													// 判断id
//													ISuperModel targetiSuperModel = (ISuperModel) targetValueCollectionObj;
//													Long targetid = targetiSuperModel.getId();
//
//													if (targetid == null) {
//
//													} else {
//														if (targetid.longValue() == sourceid2.longValue()) {
//															copyProperties(sourceiSuperModelobject2, targetiSuperModel);
//															flag = true;
//														}
//													}
//
//												}
//											}
//											//
//											if (!flag) {
//												targetValueCollection1.add(sourceValueCollection2object);
//											}
//										}
//
//									}
//								}
//								targetWriteMethod.invoke(target, targetValue);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 获取需要新增的元素
	 * 
	 * @param targetValueCollection
	 * @param sourceValueCollection
	 * @return
	 */
	private static Collection getInsertElement(Collection<ISuperModel> targetValueCollection, Collection<ISuperModel> sourceValueCollection) {
		
		Set set = new HashSet();
		List<Long> targetIds = new ArrayList<>();
		List<Long> sourceIds = new ArrayList<>();
		
		for (ISuperModel source : sourceValueCollection) {
			Long sourceId = source.getId();
			if (sourceId == null) {
				set.add(source);
			} else {
				sourceIds.add(sourceId);
			}
		}
		
		for (ISuperModel target : targetValueCollection) {
			Long targetId = target.getId();
			if (targetId == null) {
				continue;
			} else {
				targetIds.add(targetId);
			}
		}
		
		List<Long> addIds = new ArrayList<>();
		for (Long sourceId : sourceIds) {
			if (!targetIds.contains(sourceId)) {
				addIds.add(sourceId);
			}
		}
		
		for (ISuperModel source : sourceValueCollection) {
			Long sourceId = source.getId();
			if (addIds.contains(sourceId)) {
				set.add(source);
			}
		}
		
		return set;
	}

	/**
	 * 获取需要更新的元素
	 * 
	 * @param targetValueCollection
	 * @param sourceValueCollection
	 * @return
	 */
	private static Collection getUpdateElement(Collection<ISuperModel> targetValueCollection,
			Collection<ISuperModel> sourceValueCollection) {
		
		Set set = new HashSet();
		for (ISuperModel target : targetValueCollection) {
			Long targetId = target.getId();
			if (targetId == null) {
				continue;
			}
			for (ISuperModel source : sourceValueCollection) {
				Long sourceId = source.getId();
				if (sourceId == null) {
					continue;
				}
				if (targetId.longValue() == sourceId.longValue()) {
					copySinglenessProperties(source, target, null);
					set.add(target);
				}
			}
		}
		
		return set;
	}

	/**
	 * 获取需要删除的元素
	 * 
	 * @param targetValueCollection1
	 * @param sourceValueCollection2
	 * @return
	 */
	private static Collection getDeleteElement(Collection<ISuperModel> targetValueCollection1,
			Collection<ISuperModel> sourceValueCollection2) {
		Set set = new HashSet();
		for (ISuperModel target : targetValueCollection1) {
			Long targetId = target.getId();
			if (targetId == null) {
				continue;
			}
			if (!StringUtil.isEmpty(sourceValueCollection2)) { // 原数据不为空或null才比较：原数据里面没有目标数据里面的数据就是需要删除的数据
				for (ISuperModel source : sourceValueCollection2) {
					Long sourceId = source.getId();
					if (sourceId == null) {
						continue;
					}
					if (targetId.longValue() == sourceId.longValue()) {

					} else {
						set.add(target);
					}
				}
			} else { // 原数据为空或null，那么目标数据全部都是需要删除的数据
				return targetValueCollection1;
			}
			
		}
		return set;
	}

	/**
	 * 
	 * @param targetPd
	 *            目標方法
	 * @param sourceValue
	 *            数据源
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	private static Object listCopy(PropertyDescriptor targetPd, Object sourceValue)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		ParameterizedType pt = (ParameterizedType) targetPd.getReadMethod().getGenericReturnType();
		Class<? extends Object> type = (Class<?>) pt.getActualTypeArguments()[0];
		sourceValue = exchange(sourceValue, type);
		return sourceValue;
	}

	private static boolean classNameEqList(String name) {
		if (name.equals("java.util.List") || name.equals("java.util.Set")) {
			return true;
		}
		return false;
	}

	/**
	 * Vo转非Vo
	 * @param value
	 * @param type
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	private static Object exchange(Object value, Class<? extends Object> type)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		if (value instanceof List) {
			List<Object> list = (List) value;
			List<Object> list2 = new ArrayList<>();
			for (Object object : list) {
				Object newInstance = type.getClassLoader().loadClass(type.getName()).newInstance();
				try {
					copyProperties2(object, newInstance, null, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				list2.add(newInstance);
			}
			return list2;
		} else if (value instanceof Set) {
			Set<Object> list = (Set) value;
			Set<Object> list2 = new HashSet<>();
			for (Object object : list) {
				Object newInstance = type.getClassLoader().loadClass(type.getName()).newInstance();
				try {
					copyProperties2(object, newInstance, null, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				list2.add(newInstance);
			}
			return list2;
		}
		return null;
	}

	/**
	 * 复制单一属性（非list属性）
	 * 
	 * @param source
	 * @param target
	 * @param editable
	 * @param ignoreProperties
	 * @throws BeansException
	 */
	private static void copySinglenessProperties(Object source, Object target, Class<?> editable,
			String... ignoreProperties) throws BeansException {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		if (editable != null) {
			if (!editable.isInstance(target)) {
				throw new IllegalArgumentException("Target class [" + target.getClass().getName()
						+ "] not assignable to Editable class [" + editable.getName() + "]");
			}
			actualEditable = editable;
		}
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

		for (PropertyDescriptor targetPd : targetPds) {
			Method targetWriteMethod = targetPd.getWriteMethod();
			String name = targetPd.getPropertyType().getName();
			if (!classNameEqList(name)) {
				if (targetWriteMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {

					// 获取set或者list 属性
					PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
					if (sourcePd != null) {
						// 获取get方法
						Method readMethod = sourcePd.getReadMethod();
						// 判断是否有gatset
						if (readMethod != null && ClassUtils.isAssignable(targetWriteMethod.getParameterTypes()[0],
								readMethod.getReturnType())) {
							try {
								//
								if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
									readMethod.setAccessible(true);
								}
								// 获取对象
								Object value = readMethod.invoke(source);
								if (!Modifier.isPublic(targetWriteMethod.getDeclaringClass().getModifiers())) {
									targetWriteMethod.setAccessible(true);
								}
								if (value != null) {
									targetWriteMethod.invoke(target, value);
								}
							} catch (Throwable ex) {
								throw new FatalBeanException(
										"Could not copy property '" + targetPd.getName() + "' from source to target",
										ex);
							}
						}
					}
				}
			}
		}
	}
	
//	public static void main(String[] args) {
//		List<Integer> list1 = new ArrayList<>();
//		List<Integer> list2 = new ArrayList<>();
//		
//		list1.add(1);
//		list1.add(2);
//		list1.add(3);
//		list1.add(4);
//		
//		list2.add(3);
//		list2.add(4);
//		list2.add(5);
//		list2.add(6);
//		list2.add(7);
//		
//		Set<Integer> list3 = new HashSet<>();
//		for (Integer id2 : list2) {
//			for (Integer id1 : list1) {
//				if (!list2.contains(id1)) {
//					list3.add(id1);
//				}
//			}
//		}
//		
//		for (Integer integer : list3) {
//			System.out.println(integer);
//		}
//	}

}
