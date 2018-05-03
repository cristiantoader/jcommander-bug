package org.ctoader.jcommander;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.CommaParameterSplitter;
import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class JCommanderListsOverwrite {

    @Test
    public void testSimpleOverwrite() {
        ParamsTest params = new ParamsTest();

        JCommander jCommander = new JCommander.Builder()
                .acceptUnknownOptions(true)
                .allowParameterOverwriting(true)
                .addObject(params)
                .build();

        String[] args = {"-foo", "something1", "-foo", "something2"};
        jCommander.parse(args);

        assertEquals("something2", params.getFoo());
    }

    @Test
    public void testListOverwrite() {
        ParamsTest params = new ParamsTest();

        JCommander jCommander = new JCommander.Builder()
                .acceptUnknownOptions(true)
                .allowParameterOverwriting(true)
                .addObject(params)
                .build();

        String[] args = {"-bars", "b1,b2,b3", "-bars", "b4"};
        jCommander.parse(args);

        assertNotNull(params.getBars());
        assertEquals(1, params.getBars().size());
        assertTrue(params.getBars().contains("b4"));
    }

    @Data
    private static class ParamsTest {
        @Parameter(names = "-foo")
        private String foo;

        @Parameter(names = "-bars", splitter = CommaParameterSplitter.class)
        private List<String> bars = new ArrayList<>();
    }
}
