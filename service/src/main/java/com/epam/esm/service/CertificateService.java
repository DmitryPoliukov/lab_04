package com.epam.esm.service;

import com.epam.esm.repository.dto.CertificateDto;
import com.epam.esm.repository.entity.Certificate;

import java.util.List;

public interface CertificateService {

    /**
     * Create certificate.
     *
     * @param certificate the certificate
     * @return the certificate
     */
    CertificateDto create(CertificateDto certificate);

    /**
     * Read all list.
     *
     * @return the list
     */
    List<CertificateDto> readAll(int page, int size);

    /**
     * Read certificate.
     *
     * @param id the id
     * @return the certificate
     */
    CertificateDto read(int id);


    /**
     * Update certificate
     *
     * @param id
     * @param certificateDto
     * @return
     */
    void update(int id, CertificateDto certificateDto);


    /**
     * Delete certificate.
     *
     * @param id
     */
    void delete(int id);

    List<CertificateDto> readCertificateWithParams(String tagName, String descriptionOrNamePart,
                                                   String sortParameter, boolean ascending,
                                                   int page, int size);

    List<CertificateDto> readBySomeTags(List<String> tags, int page, int size);
}
