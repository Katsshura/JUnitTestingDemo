package com.katsshura.demo.junit.api.util.source;

import com.katsshura.demo.junit.api.util.builder.BaseDtoJsonBuilder;
import org.json.JSONException;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.nio.file.ProviderNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
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
        return objSource.stream();
    }

    private Collection<Arguments> create() throws JSONException {
        final var builder = getBuilder(target);

        if (builder.isEmpty()) {
            throw new ProviderNotFoundException("Could not found valid BaseDtoJsonBuilder!");
        }

        return create(builder.get());
    }

    private Collection<Arguments> create(final BaseDtoJsonBuilder builder) throws JSONException {
        final var list = new ArrayList<Arguments>();

        for (int i = 0; i < listOfObjectsSize; i++) {
            Map obj = invalidFields
                    ? builder.buildInvalidJsonObject()
                    : builder.buildValidJsonObject();

            list.add(Arguments.arguments(obj.values().stream().findFirst().get().toString(),
                    obj.keySet().stream().findFirst().get()));
        }

        return list;
    }
}
