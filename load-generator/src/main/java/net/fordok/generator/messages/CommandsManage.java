package net.fordok.generator.messages;

import net.fordok.service.dto.Run;

import java.io.Serializable;

/**
 * User: Fordok
 * Date: 1/4/2015
 * Time: 9:15 PM
 */
public class CommandsManage implements Serializable {
    public static final class Start extends CommandsManage {
        private final Run run;

        public Start(Run run) {
            this.run = run;
        }

        public Run getRun() {
            return run;
        }
    }
    public static final class Stop extends CommandsManage{}
}
