package com.sudoplay.sudomod.mod.container;

/**
 * Created by codetaylor on 2/22/2017.
 */
public interface IModContainerCache {

  <T> void put(Class<T> tClass, T object);

  <T> T get(Class<T> tClass);

}
