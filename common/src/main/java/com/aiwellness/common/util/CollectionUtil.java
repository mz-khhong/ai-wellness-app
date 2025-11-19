package com.aiwellness.common.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * com.aiwellness.common.util
 * <p>
 * CollectionUtil
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 14.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 14.    메가존 시스템            최초 생성
 * </pre>
 */
public class CollectionUtil {
    
    public static boolean isEmpty(Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }
    
    public static boolean isNotEmpty(Collection<?> collection) {
        return CollectionUtils.isNotEmpty(collection);
    }
    
    public static <T> List<T> filter(List<T> list, java.util.function.Predicate<T> predicate) {
        if (isEmpty(list)) {
            return List.of();
        }
        return list.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
    
    public static <T, R> List<R> map(List<T> list, java.util.function.Function<T, R> mapper) {
        if (isEmpty(list)) {
            return List.of();
        }
        return list.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }
}

