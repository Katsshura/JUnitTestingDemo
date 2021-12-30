package com.katsshura.demo.junit.api.util.source;

import com.katsshura.demo.junit.api.util.builder.BaseDtoJsonBuilder;
import lombok.NoArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.nio.file.ProviderNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static com.katsshura.demo.junit.api.util.ApplicationContextProvider.getApplicationContext;

public class RandomDTOJsonProvider implements ArgumentsProvider, AnnotationConsumer<RandomJSONSource> {

    private final Collection<BaseDtoJsonBuilder> dtoJsonBuilderList;
    private Class<?> target;
    private long listOfObjectsSize;
    private boolean invalidFields;


    public RandomDTOJsonProvider() {
        var beanFactory = getApplicationContext();
        this.dtoJsonBuilderList = beanFactory.getBeansOfType(BaseDtoJsonBuilder.class).values();
    }

    @Override
    public void accept(RandomJSONSource randomJSONSource) {
        this.listOfObjectsSize = randomJSONSource.interactions();
        this.target = randomJSONSource.target();
        this.invalidFields = randomJSONSource.invalidFields();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        final var objSource = create();
        return toStream(objSource);
    }

    private Collection<JSONObject> create() throws JSONException {
        final var builder = dtoJsonBuilderList.stream()
                .filter(b -> b.getClass().equals(target))
                .findFirst();

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
