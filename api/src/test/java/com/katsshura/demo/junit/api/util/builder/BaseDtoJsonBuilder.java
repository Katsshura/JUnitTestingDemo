package com.katsshura.demo.junit.api.util.builder;

import org.json.JSONException;
import org.json.JSONObject;

public interface BaseDtoJsonBuilder {
    JSONObject buildValidJsonObject() throws JSONException;
    JSONObject buildInvalidJsonObject() throws JSONException;
}
