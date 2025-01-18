package dev.matias.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {
    public static void copyNonNullProperties(Object source, Object target) {
        // copy all of the properties to another object
        BeanUtils.copyProperties(source, target, getNullProperties(source));
    }

    public static String[] getNullProperties(Object source) {

        final BeanWrapper src = new BeanWrapperImpl(source);

        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();

        for (PropertyDescriptor property : pds) {

            Object srcValue = src.getPropertyValue(property.getName());

            if (srcValue == null) {
                emptyNames.add(property.getName());
            }
        }

        String[] results = new String[emptyNames.size()];
        return emptyNames.toArray(results);
    }
}
