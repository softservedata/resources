package org.registrator.community.enumLoader;

import org.hibernate.Session;

public interface SessionAction {
    void run(Session session) throws Exception;
}
