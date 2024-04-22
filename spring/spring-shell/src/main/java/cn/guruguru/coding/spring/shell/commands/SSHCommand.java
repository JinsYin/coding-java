package cn.guruguru.coding.spring.shell.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;


import static java.lang.String.format;

/**
 * SSH Command
 *
 * Help:
 * <pre>
 *     SSH Command
 *          ssh: connect to remote server
 * </pre>
 *
 * Usages:
 * <pre>
 *     1. ssh -s localhost
 * </pre>
 */
@ShellComponent
public class SSHCommand
{
    Logger log = LoggerFactory.getLogger(SSHCommand.class);

    // key 属性代表命令名称（如果没有指定，默认是方法名，如果是驼峰命名则变成 "-"），value 属性描述命令，另外还有一个 prefix 属性默认值为 "-"
    @ShellMethod(key = "ssh", value = "connect to remote server")
    public void ssh(
            @ShellOption(value = "-s", defaultValue = "127.0.0.1") String remoteServer)
    {
        log.info(format("Logged to machine '%s'", remoteServer));
    }
}
