package com.katsshura.demo.junit.api.util.source;

import com.katsshura.demo.junit.api.util.builder.BaseDtoJsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.nio.file.ProviderNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import static com.katsshura.demo.junit.api.util.DtoJsonBuilderFactory.getBuilder;


public class RandomDTOJsonProvider implements ArgumentsProvider, AnnotationConsumer<RandomJSONSource> {

    private Class<?> target;
    private long listOfObjectsSize;
    private boolean invalidFields;

    @Override
    public void accept(RandomJSONSource randomJSONSource) {
        this.listOfObjectsSize = randomJSONSource.interactions();
        this.target = randomJSONSource.targetBuilder();
        this.invalidFields = randomJSONSource.invalidFields();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        final var objSource = create();
        return toStream(objSource);
    }

    private Collection<JSONObject> create() throws JSONException {
        final var builder = getBuilder(target);

        if (builder.isEmpty()) {
            throw new ProviderNotFoundException("Could not found valid BaseDtoJsonBuilder!");
        }

        return create(builder.get());
    }

    private Collection<JSONObject> create(final BaseDtoJsonBuilder builder) throws JSONException {
        final var list = new ArrayList<JSONObject>();

        for (int i = 0; i < listOfObjectsSize; i++) {
            JSONObject obj = invalidFields
                    ? builder.buildInvalidJsonObject()
                    : builder.buildValidJsonObject();
            list.add(obj);
        }

        return list;
    }

    private Stream<Arguments> toStream(Collection<JSONObject> jsonObjects) {
        return jsonObjects.stream()
                .map(JSONObject::toString)
                .map(Arguments::arguments);
    }
}
