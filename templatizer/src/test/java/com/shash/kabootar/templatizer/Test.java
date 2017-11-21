package com.shash.kabootar.templatizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheException;
import com.github.mustachejava.MustacheFactory;
import com.google.common.collect.ImmutableMap;
import com.shash.kabootar.templatizer.domain.Template;
import lombok.Builder;
import lombok.Data;

import java.io.*;

/**
 * @author shashankgautam
 */
public class Test {

    static ObjectMapper mapper = new ObjectMapper();

    @Data
    @Builder
    static class XYZ {
        private String name;
    }


    public static void main(String[] args) throws IOException, MustacheException {
        mapper.readValue("{\"name\":\"shash\"}", XYZ.class);
        InputStream stream = new ByteArrayInputStream("Hi {{name}}".getBytes());
        InputStreamReader reader = new InputStreamReader(stream);

        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = (Mustache) mf.compile(str);
//        Mustache mustache = (Mustache) mf.compile(reader, "");
        System.out.println("=======\n\n\n\n");
        mustache.execute(new PrintWriter(System.out), ImmutableMap.builder().put("name","shash").build()).flush();
        System.out.println("\n=======\n\n\n\n");
//
//        System.out.println(new ObjectMapper().writeValueAsString(new Template()));

        System.out.println(mapper.writeValueAsString(XYZ.builder().name("shash").build()));
    }

    static String str = "temp.mustache";
}
