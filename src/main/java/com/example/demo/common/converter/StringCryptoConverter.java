package com.example.demo.common.converter;

import com.example.demo.common.cipher.AESCipher;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StringCryptoConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return AESCipher.encode(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return AESCipher.decode(dbData);
    }
}