package net.fordok.service.storage;

import net.fordok.service.dto.Session;

import java.util.List;

/**
 * Created by fordok on 10/9/2015.
 */
public interface Storage {
    List<Session> getSessions();
    Session getSessionById(String sessionId);
    Session saveSession(Session session);
}
