package com.epam.esm.repository.entity;

import com.epam.esm.repository.dto.CertificateDto;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Audited
@Table(name = "gift_certificate")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column private String name;
    @Column private String description;
    @Column private Double price;
    @Column private Integer duration;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "gift_certificate_m2m_tag",
            joinColumns = {@JoinColumn(name = "gift_certificate_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Tag> tags;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "certificate")
    private List<Order> orders;

    public Certificate() {
    }

    public Certificate(Integer id, String name, String description, Double price, Integer duration,
                       LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public Certificate(String name, String description, Double price, Integer duration,
                       LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public Certificate(Integer id, Integer duration, List<Tag> tags) {
        this.id = id;
        this.duration = duration;
        this.tags = tags;
    }

    public Certificate(Integer id, Integer duration) {
        this.id = id;
        this.duration = duration;
    }

    public Certificate(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Certificate(Integer id, String name, String description, Double price, Integer duration, LocalDateTime createDate, LocalDateTime lastUpdateDate, List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    public CertificateDto toDto() {
        CertificateDto certificateDto = new CertificateDto();
        certificateDto.setId(this.id);
        certificateDto.setName(this.name);
        certificateDto.setDescription(this.description);
        certificateDto.setPrice(this.price);
        certificateDto.setDuration(this.duration);
        certificateDto.setCreateDate(this.createDate);
        certificateDto.setLastUpdateDate(this.lastUpdateDate);
        certificateDto.setTagsDto(this.tags.stream()
                .map(Tag::toDto)
                .collect(Collectors.toList()));
        return certificateDto;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getDuration() {
        return duration;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Certificate{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", price=").append(price);
        sb.append(", duration=").append(duration);
        sb.append(", createDate=").append(createDate);
        sb.append(", lastUpdateDate=").append(lastUpdateDate);
        sb.append(", tags=").append(tags);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Certificate that = (Certificate) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null)
            return false;
        if (lastUpdateDate != null
                ? !lastUpdateDate.equals(that.lastUpdateDate)
                : that.lastUpdateDate != null) return false;
        return tags != null ? tags.equals(that.tags) : that.tags == null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;        int result = 1;
        result = prime * result + (id != null ? id.hashCode() : 0);
        result = prime * result + (name != null ? name.hashCode() : 0);
        result = prime * result + (description != null ? description.hashCode() : 0);
        result = prime * result + (price != null ? price.hashCode() : 0);
        result = prime * result + (duration != null ? duration.hashCode() : 0);
        result = prime * result + (createDate != null ? createDate.hashCode() : 0);
        result = prime * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = prime * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }


}
