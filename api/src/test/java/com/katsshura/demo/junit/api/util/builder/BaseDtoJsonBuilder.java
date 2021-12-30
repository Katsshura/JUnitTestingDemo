package com.katsshura.demo.junit.api.util.builder;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseDtoJsonBuilder {
    public abstract JSONObject buildValidJsonObject() throws JSONException;
    public abstract JSONObject buildInvalidJsonObject() throws JSONException;
}
