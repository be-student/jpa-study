package com.bestudent.embedded.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class StringCollection {

    private String stringCollectionValue;

    @OneToMany(mappedBy = "stringEntityId")
    private List<StringEntity> stringEntityList = new ArrayList<>();
}
