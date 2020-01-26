package ru.inspector_files.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.inspector_files.HibernateUtil;
import ru.inspector_files.model.Document;

public class DocumentRepositoryImpl implements DocumentRepository {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public Document save(Document document) {
        Session currentSession = sessionFactory.openSession();
        currentSession.beginTransaction();
        currentSession.save(document);
        currentSession.getTransaction().commit();
        currentSession.close();
        return document;
    }
}
