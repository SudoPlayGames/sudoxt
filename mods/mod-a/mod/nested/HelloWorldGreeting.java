package mod.nested;

/**
 * Created by codetaylor on 2/22/2017.
 */
public class HelloWorldGreeting implements
    IGreeting {

  @Override
  public String getGreeting() {
    return "Hello World!";
  }
}
