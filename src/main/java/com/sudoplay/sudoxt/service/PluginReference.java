package com.sudoplay.sudoxt.service;

import com.sudoplay.sudoxt.classloader.asm.callback.InjectedCallback;
import com.sudoplay.sudoxt.container.Container;

/**
 * Created by codetaylor on 2/22/2017.
 */
public class PluginReference<P> {

  public interface InvokeVoidHandler<P> {
    void invoke(P plugin);
  }

  public interface InvokeReturnHandler<P, R> {
    R invoke(P plugin);
  }

  private Class<P> pClass;
  private String resourceString;
  private Container container;

  /* package */ PluginReference(Class<P> pClass, String resourceString, Container container) {
    this.pClass = pClass;
    this.resourceString = resourceString;
    this.container = container;
  }

  /* package */ void preLoad() throws PluginException {

    try {
      this.get();

    } catch (Exception e) {
      throw this.getException(e);
    }
  }

  public void invoke(InvokeVoidHandler<P> handler) throws PluginException {

    try {
      InjectedCallback.DELEGATE = this.container.getCallbackDelegate();
      handler.invoke(this.get());

    } catch (Exception e) {
      throw this.getException(e);
    }
  }

  private PluginException getException(Exception e) {
    return new PluginException(
        String.format(
            "[%s]: %s",
            e.getClass().getSimpleName(),
            e.getMessage()
        ),
        e
    );
  }

  public <R> R invoke(Class<R> rClass, InvokeReturnHandler<P, R> handler) throws PluginException {

    try {
      InjectedCallback.DELEGATE = this.container.getCallbackDelegate();
      return handler.invoke(this.get());

    } catch (Exception e) {
      throw this.getException(e);
    }
  }

  public String getReport() {
    return InjectedCallback.DELEGATE.getReport();
  }

  private P get() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
    return this.container.getPlugin(this.resourceString, this.pClass);
  }
}