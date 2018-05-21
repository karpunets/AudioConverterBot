package com.karpunets.audioConverterBot.services;


import java.util.Set;

public interface AudioConverterService {

    byte[] convert(String fileUrl, String type);

    Set<String> getSupportedType();
}
