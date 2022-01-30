package com.epam.esm.repository.dao.impl;

import com.epam.esm.repository.config.DaoConfig;
import com.epam.esm.repository.entity.Certificate;
import com.epam.esm.repository.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase
@SpringBootTest(classes = DaoConfig.class)
class CertificateDaoImplTest {


    private final CertificateDaoImpl certificateDao;

    @Autowired
    public CertificateDaoImplTest(CertificateDaoImpl certificateDao) {
        this.certificateDao = certificateDao;
    }

    private static final Tag TAG_2 = new Tag(2, "tagName3");

    private static final Certificate GIFT_CERTIFICATE_1 = new Certificate(1, "giftCertificate1",
            "description1", 10.10, 1, LocalDateTime.parse("2020-08-29T06:12:15"),
            LocalDateTime.parse("2020-08-29T06:12:15"), Arrays.asList(new Tag(2, "tagName3"), new Tag(5, "tagName2")));
    private static final Certificate GIFT_CERTIFICATE_2 = new Certificate(2, "giftCertificate3",
            "description3", 30.30, 3, LocalDateTime.parse("2019-08-29T06:12:15"),
            LocalDateTime.parse("2019-08-29T06:12:15"), Collections.singletonList(new Tag(2, "tagName3")));
    private static final Certificate GIFT_CERTIFICATE_3 = new Certificate(3, "giftCertificate2",
            "description2",20.20, 2, LocalDateTime.parse("2018-08-29T06:12:15"),
            LocalDateTime.parse("2018-08-29T06:12:15"), Collections.singletonList(new Tag(3, "tagName5")));

    private static final String SORT_PARAMETER = "ASC";
    private static final int PAGE = 0;
    private static final int SIZE = 5;


    @Test
    void read() {
        Optional<Certificate> actual = certificateDao.read(GIFT_CERTIFICATE_2.getId());
        Optional<Certificate> expected = Optional.of(GIFT_CERTIFICATE_2);

        assertEquals(expected, actual);
    }

    @Test
    void readAll() {
        List<Certificate> actual = certificateDao.readAll(PAGE, SIZE);
        List<Certificate> expected = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3);

        assertEquals(expected, actual);
    }
}