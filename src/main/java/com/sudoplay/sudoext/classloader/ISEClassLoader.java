package com.sudoplay.sudoext.classloader;

/**
 * Created by codetaylor on 2/22/2017.
 */
public interface ISEClassLoader {

  Class<?> loadClass(String name) throws ClassNotFoundException;

  Class<?> loadClassWithoutDependencyCheck(String name) throws ClassNotFoundException;
}