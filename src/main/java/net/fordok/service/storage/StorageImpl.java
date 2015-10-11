package net.fordok.service.storage;

import net.fordok.service.dto.Session;
import net.fordok.service.dto.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by fordok on 10/8/2015.
 */
public class StorageImpl implements Storage {
    private static List<Session> sessions = new ArrayList<Session>();

    public StorageImpl() {
        Session session1 = new Session("session1");
        session1.setSessionId(UUID.randomUUID().toString());
        session1.setStartTs(new Date());
        session1.setStatus("Finished");
        Session session2 = new Session("session2");
        session2.setSessionId(UUID.randomUUID().toString());
        session2.setStartTs(new Date());
        session2.setStatus("Finished");
        sessions.add(session1);
        sessions.add(session2);
    }

    @Override
    public List<Session> getSessions() {
        return sessions;
    }

    @Override
    public Session getSessionById(String sessionId) {
        for (Session session : sessions) {
            if (session.getSessionId().equals(sessionId)) {
                return session;
            }
        }
        return null;
    }

    @Override
    public Session saveSession(Session session) {
        sessions.add(session);
        return session;
    }

    @Override
    public List<Task> getTasksBySessionId(String sessionId) {
        return getSessionById(sessionId).getTasks();
    }
}
