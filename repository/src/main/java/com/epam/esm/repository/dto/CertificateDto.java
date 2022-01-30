package com.epam.esm.repository.dto;

import com.epam.esm.repository.entity.Certificate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties({ "orders" })
public class CertificateDto extends RepresentationModel<CertificateDto> {

    private Integer id;

    @Size(min = 1,max = 45, message = "Certificate name length should be from 1 to 45")
    private String name;

    @Size(min = 1,max = 45, message = "Certificate description length should be from 1 to 45")
    private String description;

    @PositiveOrZero(message = "Certificate price should positive or zero")
    private Double price;

    @PositiveOrZero(message = "Certificate duration should positive or zero")
    private Integer duration;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm'Z'")
    private LocalDateTime createDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm'Z'")
    private LocalDateTime lastUpdateDate;

    @JsonProperty("tags")
    private List<TagDto> tagsDto;

    @JsonIgnore
    private List<OrderDto> orders;

    public CertificateDto() {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    public List<TagDto> getTagsDto() {
        return tagsDto;
    }

    public void setTagsDto(List<TagDto> tagsDto) {
        this.tagsDto = tagsDto;
    }

    public List<OrderDto> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDto> orders) {
        this.orders = orders;
    }

    public Certificate toEntity() {
        Certificate entity = new Certificate();
        entity.setId(this.id);
        entity.setName(this.name);
        entity.setDescription(this.description);
        entity.setPrice(this.price);
        entity.setDuration(this.duration);
        entity.setCreateDate(this.createDate);
        entity.setLastUpdateDate(this.lastUpdateDate);
        entity.setTags(this.tagsDto.stream()
                .map(TagDto::toEntity)
                .collect(Collectors.toList()));
        return entity;
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
        sb.append(", tags=").append(tagsDto);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CertificateDto that = (CertificateDto) o;
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
        return tagsDto != null ? tagsDto.equals(that.tagsDto) : that.tagsDto == null;
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
        result = prime * result + (tagsDto != null ? tagsDto.hashCode() : 0);
        return result;
    }

}
