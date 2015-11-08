package net.fordok.generator.core;

import net.fordok.service.dto.Run;

/**
 * User: Fordok
 * Date: 1/3/2015
 * Time: 4:18 PM
 */
public interface LoadGenerator {
    void init();
    void start(Run run);
    void stop();
}
