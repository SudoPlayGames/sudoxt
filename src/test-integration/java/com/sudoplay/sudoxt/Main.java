package com.sudoplay.sudoxt;

import com.sudoplay.sudoxt.api.AncillaryPlugin;
import com.sudoplay.sudoxt.api.LoggerStaticInjector;
import com.sudoplay.sudoxt.classloader.asm.callback.NoOpCallbackDelegateFactory;
import com.sudoplay.sudoxt.classloader.asm.filter.AllowedJavaUtilClassFilter;
import com.sudoplay.sudoxt.classloader.asm.transform.SEByteCodeTransformerBuilder;
import com.sudoplay.sudoxt.classloader.filter.AllowAllClassFilter;
import com.sudoplay.sudoxt.classloader.filter.IClassFilter;
import com.sudoplay.sudoxt.classloader.security.SEServicePolicy;
import com.sudoplay.sudoxt.service.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sudoplay.sudoxt.api.Plugin;

import java.io.FilePermission;
import java.nio.file.Paths;
import java.security.AllPermission;
import java.security.Permissions;
import java.security.Policy;
import java.util.List;

/**
 * Created by codetaylor on 2/18/2017.
 */
public class Main {

  static {
    Policy.setPolicy(
        new SEServicePolicy(
            () -> {
              Permissions permissions = new Permissions();
              permissions.add(new AllPermission());
              return permissions;
            },
            path -> {
              Permissions permissions = new Permissions();
              permissions.add(new FilePermission(path.toString() + "/-", "read"));
              return permissions;
            }
        )
    );
    System.setSecurityManager(new SecurityManager());
  }

  private static final Logger LOG = LoggerFactory.getLogger(Main.class);

  @Test
  public void test() throws SEServiceInitializationException, PluginException {

    SEService service = new SEServiceBuilder(
        new SEConfigBuilder()
            .setCompressedFileExtension(".lsm")
            .setLocation(Paths.get("../mods"))
            .setDataLocation(Paths.get("mod-data"))
            .setTempLocation(Paths.get("mods-temp"))
            .setMetaFilename("mod-info.json")
            .setApiVersion("1.0"))
        .addStaticInjector(new LoggerStaticInjector())
        .addClassLoaderClassFilter(new AllowAllClassFilter())
        .setCallbackDelegateFactory(NoOpCallbackDelegateFactory.INSTANCE) // testing
        .setByteCodeTransformerBuilder(new SEByteCodeTransformerBuilder()
            //.setByteCodePrinter(new StdOutByteCodePrinter())
            .addClassFilter(new AllowedJavaUtilClassFilter())
            .addClassFilter(new IClassFilter() {
              @Override
              public boolean isAllowed(String name) {
                return name.startsWith("mod.")
                    || name.startsWith("com.sudoplay.math.")
                    || name.startsWith("com.sudoplay.sudoxt.api.");
              }
            })
        )
        .create();

    PluginReference<Plugin> pluginA = service.getPlugin("mod_a:mod.ModPlugin", Plugin.class);
    PluginReference<Plugin> pluginB = service.getPlugin("mod_b:mod.ModPlugin", Plugin.class);

    List<PluginReference<AncillaryPlugin>> referenceList = service.getRegisteredPlugins("blue", AncillaryPlugin.class);

    System.out.println("--- Preload ---");
    service.preload((containerId, resource, percentage, timeMilliseconds, throwable) -> {
      System.out.println(String.format(
          "%s:%s %f %d", containerId, resource, percentage, timeMilliseconds
      ));

      if (throwable != null) {
        throwable.printStackTrace();
      }
    });
    System.out.println("--- End Preload ---");

    pluginA.invoke(Plugin::onGreeting);
    System.out.println(pluginA.getReport());
    System.out.println("---");

    pluginB.invoke(Plugin::onGreeting);
    System.out.println(pluginB.getReport());
    System.out.println("---");

    for (PluginReference<AncillaryPlugin> reference : referenceList) {
      reference.invoke(AncillaryPlugin::doStuff);
    }

    service.disposeFolders();
  }

  private interface RunnableException {
    void run() throws Exception;
  }

  private void expect(RunnableException runnable) {

    try {
      runnable.run();
      //Assert.fail();

    } catch (Exception e) {
      // expected
      LOG.error(e.getMessage());
    }
  }

}