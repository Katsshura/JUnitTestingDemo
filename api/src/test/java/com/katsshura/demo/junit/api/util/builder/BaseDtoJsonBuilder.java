package com.katsshura.demo.junit.api.util.builder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public interface BaseDtoJsonBuilder <T> {
    Map<T, JSONObject> buildValidJsonObject() throws JSONException;
    Map<T, JSONObject> buildInvalidJsonObject() throws JSONException;
}
