package com.epam.esm.repository.dao.impl;

import com.epam.esm.repository.dao.PaginationHandler;
import org.springframework.stereotype.Component;

import javax.persistence.TypedQuery;

@Component
public class PaginationHandlerImpl implements PaginationHandler {
    @Override
    public void setPageToQuery(TypedQuery<?> typedQuery, int page, int size) {
        typedQuery.setFirstResult((page - 1) * size);
        typedQuery.setMaxResults(size);
    }
}
