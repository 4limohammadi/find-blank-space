package com.example.assignnumbertoclient.client;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@Setter
public class Client {
    @Id
    @Column(name = "client_number", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long clientNumber;

}
