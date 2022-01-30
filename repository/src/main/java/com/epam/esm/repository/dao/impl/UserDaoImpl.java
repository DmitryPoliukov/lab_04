package com.epam.esm.repository.dao.impl;

import com.epam.esm.repository.dao.PaginationHandler;
import com.epam.esm.repository.dao.UserDao;
import com.epam.esm.repository.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    private final PaginationHandler paginationHandler;
    private final EntityManager entityManager;

    public UserDaoImpl(PaginationHandler paginationHandler, EntityManager entityManager) {
        this.paginationHandler = paginationHandler;
        this.entityManager = entityManager;
    }

    @Override
    public Optional<User> read(int id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> readAll(int page, int size) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        Root<User> from = criteriaQuery.from(User.class);
        CriteriaQuery<User> select = criteriaQuery.select(from);

        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        countQuery.select(builder.count(countQuery.from(User.class)));

        TypedQuery<User> typedQuery = entityManager.createQuery(select);
        paginationHandler.setPageToQuery(typedQuery, page, size);
        return typedQuery.getResultList();

    }
}
