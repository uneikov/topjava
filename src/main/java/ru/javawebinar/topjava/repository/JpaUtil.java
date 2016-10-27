package ru.javawebinar.topjava.repository;

public interface JpaUtil {
    void clear2ndLevelHibernateCache();
    default JpaUtil getUtil(){
        return new JpaUtilImpl();
    }
}
