package ru.javawebinar.topjava.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JpaUtilImpl implements JpaUtil{

    private static final Logger LOG = LoggerFactory.getLogger(JpaUtilImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public void clear2ndLevelHibernateCache() {

        Session s = (Session) em.getDelegate();
        SessionFactory sf = s.getSessionFactory();
        LOG.info("/\\/\\/\\/\\/\\In clear2ndLevelHibernateCache() with {} /\\/\\/\\/\\", sf);
//        sf.evict(User.class);
//        sf.getCache().evictEntity(User.class, BaseEntity.START_SEQ);
//        sf.getCache().evictEntityRegion(User.class);
        sf.getCache().evictQueryRegions();
        sf.getCache().evictDefaultQueryRegion();
        sf.getCache().evictCollectionRegions();
        sf.getCache().evictEntityRegions();
    }
}
