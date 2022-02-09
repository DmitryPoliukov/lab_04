package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.CertificateDao;
import com.epam.esm.repository.dto.CertificateDto;
import com.epam.esm.repository.entity.Certificate;
import com.epam.esm.repository.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CertificateServiceImplTest {

    CertificateDao certificateDao = mock(CertificateDao.class);
    CertificateServiceImpl certificateService =
            new CertificateServiceImpl(certificateDao);

    private static final Tag TAG_1 = new Tag(1, "tagName1");
    private static final Tag TAG_2 = new Tag(2, "tagName2");

    private static final Certificate CERTIFICATE_1 = new Certificate(1, "giftCertificate1",
            "description1", 10.1, 1, LocalDateTime.parse("2021-01-19T06:12:15.156"),
            LocalDateTime.parse("2021-01-19T06:12:15.156"), Arrays.asList(new Tag(1, "tagName1"),
            new Tag(2, "tagName2"), new Tag(3, "tagName3")));

    private static final Certificate CERTIFICATE_2 = new Certificate(2, "giftCertificate3",
            "description3", 30.3, 3, LocalDateTime.parse("2021-01-17T06:12:15.156"),
            LocalDateTime.parse("2021-01-17T06:12:15.156"), Collections.singletonList(new Tag(2, "tagName2")));

    private static final Certificate CERTIFICATE_3 = new Certificate(3, "giftCertificate2",
            "description2", 20.2, 2, LocalDateTime.parse("2021-01-15T06:12:15.156"),
            LocalDateTime.parse("2021-01-15T06:12:15.156"), Arrays.asList(new Tag(1, "tagName1"),
            new Tag(2, "tagName2")));

    private static final LocalDateTime UPDATED_DATE = LocalDateTime.parse("2021-01-15T06:12:15.156");

    private static final String SORT_PARAMETER = "name";
    private static final int PAGE = 0;
    private static final int SIZE = 5;


    @Test
    void readAll() {
        List<Certificate> giftCertificates = Arrays.asList(CERTIFICATE_1, CERTIFICATE_2, CERTIFICATE_3);
        when(certificateDao.readAll(PAGE, SIZE)).thenReturn(giftCertificates);
        List<Certificate> actual = certificateService.readAll(PAGE, SIZE).stream()
                .map(CertificateDto::toEntity)
                .collect(Collectors.toList());


        List<Certificate> expected = giftCertificates;
        assertEquals(expected, actual);
    }

    @Test
    void read() {
        when(certificateDao.read(CERTIFICATE_2.getId())).thenReturn(Optional.of(CERTIFICATE_2));
        Certificate actual = certificateService.read(CERTIFICATE_2.getId()).toEntity();
        Certificate expected = CERTIFICATE_2;

        assertEquals(expected, actual);
    }

    @Test
    void update() {
        when(certificateDao.read(CERTIFICATE_3.getId())).thenReturn(Optional.of(CERTIFICATE_3));

        certificateService.update(CERTIFICATE_3.getId(), CERTIFICATE_3.toDto());
        Certificate actual = certificateService.read(CERTIFICATE_3.getId()).toEntity();
        Certificate expected = CERTIFICATE_3;

        assertEquals(expected, actual);

    }

    @Test
    void readCertificateWithParams() {
        List<Certificate> giftCertificates = Arrays.asList(CERTIFICATE_2, CERTIFICATE_1);
        when(certificateDao.readCertificateWithParams(TAG_2.getName(), null,
                SORT_PARAMETER, false, PAGE, SIZE )).thenReturn(giftCertificates);

        List<Certificate> actual = certificateService.readCertificateWithParams(TAG_2.getName(), null,
                SORT_PARAMETER, false, PAGE, SIZE )
                .stream().map(CertificateDto::toEntity).collect(Collectors.toList());
        List<Certificate> expected = giftCertificates;

        assertEquals(expected, actual);
    }

    @Test
    void readBySomeTags() {
        List<Certificate> giftCertificates = Arrays.asList(CERTIFICATE_1, CERTIFICATE_3);
        List<String> tagNamesList = Arrays.asList(TAG_1.getName(), TAG_2.getName());
        when(certificateDao.readCertificatesByTagNames(tagNamesList, PAGE, SIZE)).thenReturn(giftCertificates);

        List<Certificate> actual = certificateService.readBySomeTags(tagNamesList, PAGE, SIZE)
                .stream().map(CertificateDto::toEntity).collect(Collectors.toList());
        List<Certificate> expected = giftCertificates;
        assertEquals(expected, actual);
    }

}