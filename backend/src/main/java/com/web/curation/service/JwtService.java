package com.web.curation.service;

import java.util.Map;

public interface JwtService {
    public <T> String create(String key, T data, String subject);
    public byte[] generateKey();
    public boolean isUsable(String jwt);
    public Map<String, Object> get(String key);
    public Object getUserId();
    public Object getUserEmail();
}