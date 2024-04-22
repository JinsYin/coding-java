package cn.guruguru.coding.spring.shell.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 * Hello Command
 *
 * Help:
 * <pre>
 *     Hello Command
 *        hello:
 * </pre>
 *
 * Usages:
 * <pre>
 *     1. hello -arg Tom
 *     2. hello Tom
 * </pre>
 */
@ShellComponent
public class HelloCommand {
    @ShellMethod(key = "hello") // 这里有个 prefix 属性，默认值是 “-”
    public String helloWorld(
            // 如果 @ShellOption 没有指定 value，则命令参数为方法参数名称（如果是驼峰命名则转成 “-”）
            @ShellOption(defaultValue = "Spring") String greetedPerson) {
        return "Hello, " + greetedPerson;
    }
}
