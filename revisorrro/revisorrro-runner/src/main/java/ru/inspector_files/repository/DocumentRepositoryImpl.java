package ru.inspector_files.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.inspector_files.model.Document;
import ru.inspector_files.utils.HibernateUtil;

import java.util.List;

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

    @Override
    public List<Document> getDocumentByLevel(int level) {
        Session currentSession = sessionFactory.openSession();
        currentSession.beginTransaction();
        Query<Document> documentQuery = currentSession.getNamedQuery(Document.GET_BY_LEVEL);
        documentQuery.setParameter("level", level);
        List<Document> documents = documentQuery.getResultList();
        currentSession.getTransaction().commit();
        currentSession.close();
        return documents;
    }
}
