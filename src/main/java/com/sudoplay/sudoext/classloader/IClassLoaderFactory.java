package com.sudoplay.sudoext.classloader;

import com.sudoplay.sudoext.container.Container;

/**
 * Created by codetaylor on 2/23/2017.
 */
public interface IClassLoaderFactory {

  SourceClassLoader create(Container container);
}
