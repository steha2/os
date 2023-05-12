package com.trickle.os.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class RandomDataGenerator {
    public static <T> T getRandomVo(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T instance = constructor.newInstance();
            Random random = new Random();
            
            // 클래스의 필드를 순회하며 랜덤한 값을 설정
            for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                
                if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
                    field.set(instance, random.nextInt());
                } else if (fieldType.equals(long.class) || fieldType.equals(Long.class)) {
                    field.set(instance, random.nextLong());
                } else if (fieldType.equals(float.class) || fieldType.equals(Float.class)) {
                    field.set(instance, random.nextFloat());
                } else if (fieldType.equals(double.class) || fieldType.equals(Double.class)) {
                    field.set(instance, random.nextDouble());
                } else if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
                    field.set(instance, random.nextBoolean());
                } else if (fieldType.equals(String.class)) {
                    field.set(instance, generateRandomString());
                }
                
                // 추가 필드 타입에 따른 설정...
            }
            
            return instance;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // 랜덤한 문자열 생성
    private static String generateRandomString() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        int length = random.nextInt(10) + 1; // 최대 길이: 10
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            stringBuilder.append(alphabet.charAt(index));
        }
        return stringBuilder.toString();
    }
}