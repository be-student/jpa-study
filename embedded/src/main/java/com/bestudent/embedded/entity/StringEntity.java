package com.bestudent.embedded.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StringEntity {

    @Id
    private Long stringEntityId;

    private String stringEntityValue;
}
