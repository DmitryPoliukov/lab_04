package com.epam.esm.repository.dao.impl;

import com.epam.esm.repository.config.DaoConfig;
import com.epam.esm.repository.dao.CertificateDao;
import com.epam.esm.repository.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@AutoConfigureTestDatabase
@SpringBootTest(classes = DaoConfig.class)
class TagDaoImplTest {

    @Autowired
    TagDaoImpl tagDao;
    @Autowired
    CertificateDao certificateDao;
    @Autowired
    EntityManager entityManager;
    @Autowired
    TransactionTemplate txTemplate;

    private static final Tag TAG_1 = new Tag(1, "tagName1");
    private static final Tag TAG_2 = new Tag(2, "tagName3");
    private static final Tag TAG_3 = new Tag(3, "tagName5");
    private static final Tag TAG_4 = new Tag(4, "tagName4");
    private static final Tag TAG_5 = new Tag(5, "tagName2");

    private static final int PAGE = 0;
    private static final int SIZE = 5;



    @Test
    void create() {
        Tag expectedTag = TAG_1;

        Tag actualTag = tagDao.create(expectedTag);

        assertNotNull(actualTag.getId());
    }

    @Test
    void read() {
        Optional<Tag> actual = tagDao.read(TAG_3.getId());
        Optional<Tag> expected = Optional.of(TAG_3);

        assertEquals(expected, actual);
    }

    @Test
    void readAll() {
        List<Tag> actual = tagDao.readAll(PAGE, SIZE);
        List<Tag> expected = Arrays.asList(TAG_1, TAG_2, TAG_3, TAG_4, TAG_5);

        assertEquals(expected, actual);
    }


    @Test
    void readByName() {
        Optional<Tag> actual = tagDao.readByName(TAG_2.getName());
        Optional<Tag> expected = Optional.of(TAG_2);

        assertEquals(expected, actual);

    }

    @Test
    void readMostWidelyTagFromUserWithHighestCostOrders() {
        Tag actual = tagDao.readMostWidelyTagFromUserWithHighestCostOrders();
        Tag expected = TAG_2;

        assertEquals(expected, actual);
    }
}