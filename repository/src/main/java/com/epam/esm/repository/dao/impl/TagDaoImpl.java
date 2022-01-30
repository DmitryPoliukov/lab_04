package com.epam.esm.repository.dao.impl;

import com.epam.esm.repository.dao.PaginationHandler;
import com.epam.esm.repository.dao.TagDao;
import com.epam.esm.repository.entity.Tag;
import com.epam.esm.repository.exception.NullParameterException;
import com.epam.esm.repository.exception.TagException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class TagDaoImpl implements TagDao {

    private final EntityManager entityManager;
    private final PaginationHandler paginationHandler;

    public TagDaoImpl(EntityManager entityManager, PaginationHandler paginationHandler) {
        this.entityManager = entityManager;
        this.paginationHandler = paginationHandler;
    }

    private static final String SQL_DELETE = "DELETE FROM tag WHERE id=:id";

    private static final String SQL_REQUEST_FOR_USER_ID_WITH_HIGHEST_COST_ORDERS =
            "(SELECT user_id FROM  "
                    + "(SELECT SUM(cost) AS summa,user_id "
                    + "FROM orders "
                    + "GROUP BY user_id) AS user_orders_cost"
                    + " ORDER BY summa desc limit 1)";

    private static final String SQL_REQUEST_FOR_WIDELY_USED_TAG_FROM_HIGHEST_COST_ORDERS_USER =
            "SELECT tag.id, tag.name "
                    + "FROM tag "
                    + "JOIN gift_certificate_m2m_tag gcm2mt on tag.id = gcm2mt.tag_id "
                    + "JOIN orders ON gcm2mt.gift_certificate_id=certificate_id "
                    + "WHERE user_id="
                    + SQL_REQUEST_FOR_USER_ID_WITH_HIGHEST_COST_ORDERS
                    + " GROUP BY tag.name "
                    + "ORDER BY count(tag.name) desc limit 1";

    @Override
    public Tag create(Tag tag) {
        if(tag == null) {
            throw new NullParameterException("Null parameter in create tag");
        }
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public Optional<Tag> read(int id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public List<Tag> readAll(int page, int size) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = builder.createQuery(Tag.class);
        Root<Tag> from = criteriaQuery.from(Tag.class);
        CriteriaQuery<Tag> select = criteriaQuery.select(from);

        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        countQuery.select(builder.count(countQuery.from(Tag.class)));

        TypedQuery<Tag> typedQuery = entityManager.createQuery(select);
        paginationHandler.setPageToQuery(typedQuery, page, size);
        return typedQuery.getResultList();
    }

    @Override
    public void delete(int id) {
        entityManager.createNativeQuery(SQL_DELETE)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public Optional<Tag> readByName(String name) {
        if(name == null) {
            throw new NullParameterException("Null parameter in read tag by name");
        }
        String hql = "Select t from Tag  t where t.name=:name";
        Query query = entityManager.createQuery(hql).setParameter("name", name);
        return query.getResultStream().findFirst();
    }

    @Override
    public Tag readMostWidelyTagFromUserWithHighestCostOrders() {
        Query q = entityManager.createNativeQuery(
                SQL_REQUEST_FOR_WIDELY_USED_TAG_FROM_HIGHEST_COST_ORDERS_USER);

        Optional<Object[]> tagValue = q.getResultStream().findFirst();
        if (tagValue.isPresent()) {
            Integer id = ((Integer) tagValue.get()[0]);
            String name = (String) tagValue.get()[1];
            return new Tag(id, name);
        } else throw new TagException("There is no any tags in orders");
    }
}
