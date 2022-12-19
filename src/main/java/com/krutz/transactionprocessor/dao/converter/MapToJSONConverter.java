/*
 * InstaRem Overseas Money Transfer.
 * https://www.instarem.com/en-in/
 *
 * Copyright (c) 2019 InstaReM
 *
 * InstaRem is an acronym of Instant Remittance.
 * InstaRem Software is designed and developed to ease the Overseas Money Transfer.
 * It allows you to transfer your money overseas with minimum cost and time.
 *
 *
 * This file is licensed and cannot be accessed by outside InstaRem.
 * It can only be accessed and modified by the authorized InstaRem Technical Teams.
 * If any unauthorized, outside of the InstaRem, user found to be unlawfully modified
 * the content of this file,  will be prosecuted under the Copyright Act
 *
 */
package com.krutz.transactionprocessor.dao.converter;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter(autoApply = true)
public class MapToJSONConverter implements AttributeConverter<Map<String, Object>, String> {

    private ObjectMapper mapper;

    public MapToJSONConverter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public MapToJSONConverter() {
        this(new ObjectMapper());
    }

    @Override
    public String convertToDatabaseColumn(Map<String, Object> entityValue) {
        entityValue = defaultIfNull(entityValue, new HashMap()  );

        try {
            return mapper.writeValueAsString(entityValue);
        } catch (Exception e) {
            log.error("Could not serialize headers", e);
        }
        return EMPTY;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String databaseValue) {
        databaseValue = defaultIfBlank(databaseValue, "{}");

        try {
            return mapper.readValue(databaseValue, Map.class);
        } catch (IOException e) {
            log.error("Could not deserialize headers from database", e);
        }
        return new HashMap();
    }



}
