package net.fordok.generator.messages;

import java.io.Serializable;

/**
 * User: Fordok
 * Date: 1/4/2015
 * Time: 9:15 PM
 */
public class CommandsManage implements Serializable {
    public static final class Start extends CommandsManage {};
    public static final class Stop extends CommandsManage{};
    public static final class Suspend extends CommandsManage{};
    public static final class Resume extends CommandsManage{};
}
