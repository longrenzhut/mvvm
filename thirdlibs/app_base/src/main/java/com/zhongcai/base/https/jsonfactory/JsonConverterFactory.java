package com.zhongcai.base.https.jsonfactory;

/*import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;*/

import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public class JsonConverterFactory extends Converter.Factory {

    public static JsonConverterFactory create() {
        return new JsonConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if(type == JSONObject.class)
            return new JsonResponseBodyConverter<JSONObject>();
        return null;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new JsonRequestBodyConverter<JSONObject>();
    }



    /**
     * Returns a {@link Converter} for converting {@code type} to a {@link String}, or null if
     * {@code type} cannot be handled by this factory. This is used to create converters for types
     * specified by {@link Field @Field}, {@link FieldMap @FieldMap} values,
     * {@link Header @Header}, {@link HeaderMap @HeaderMap}, {@link Path @Path},
     * {@link Query @Query}, and {@link QueryMap @QueryMap} values.
     */
//    public Converter<?, String> stringConverter(Type type, Annotation[] annotations,
//                                                Retrofit retrofit) {
//        return new JsonRequestBodyConverter<JSONObject>();
//    }


//    public Converter<?, String> stringConverter(Type type, Annotation[] annotations,
//                                                Retrofit retrofit) {
//        return new StringConverter<String>();
//    }

    /*public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        return new JsonResponseBodyConverter<JSONObject>();
    }

    public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
        return new JsonRequestBodyConverter<JSONObject>();
    }*/
}
