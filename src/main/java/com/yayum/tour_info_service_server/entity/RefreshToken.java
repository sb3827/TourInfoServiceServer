package com.yayum.tour_info_service_server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken extends BaseEntity{
    @Id
    private Long key; // mno
    private String value; // token

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}
