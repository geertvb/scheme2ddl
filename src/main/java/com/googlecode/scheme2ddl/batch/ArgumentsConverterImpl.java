package com.googlecode.scheme2ddl.batch;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Properties;

import static org.apache.commons.lang.StringUtils.*;

@Component
public class ArgumentsConverterImpl implements ArgumentsConverter {

    public boolean in(String value, String... values) {
        return Arrays.asList(values).contains(value);
    }

    public String removeSurroundingQuotes(String value) {
        if (startsWith(value, "'") && endsWith(value, "'")) {
            value = removeStart(value, "'");
            value = removeEnd(value, "'");
        }
        return value;
    }

    @Override
    public Properties getProperties(String... args) {
        Properties properties = new Properties();

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            if (in(arg, "-h", "--help")) {
                properties.put("justPrintUsage", "true");
            } else if (in(arg, "-u", "--url")) {
                properties.put("dbUrl", args[++i]);
            } else if (in(arg, "-U", "--username")) {
                String user = args[++i];
                if (args.length >= i + 1 && equalsIgnoreCase(args[i + 1], "as")) {
                    user += " " + args[++i] + " " + args[++i];
                    //isLaunchedByDBA = true;
                }
                properties.put("username", user);
            } else if (in(arg, "-P", "--password")) {
                properties.put("password", args[++i]);
            } else if (in(arg, "-o", "--output")) {
                properties.put("outputPath", args[++i]);
            } else if (in(arg, "-s", "--schemas")) {
                properties.put("schemas", args[++i]);
            } else if (in(arg, "-p", "--parallel")) {
                properties.put("parallelCount", Integer.parseInt(args[++i]));
            } else if (in(arg, "-tc", "--test-connection")) {
                properties.put("justTestConnection", "true");
            } else if (in(arg, "--stop-on-warning")) {
                properties.put("stopOnWarning", "true");
            } else if (in(arg, "-rsv", "--replace-sequence-values")) {
                properties.put("replaceSequenceValues", "true");
            } else if (in(arg, "-c", "--config")) {
                properties.put("customConfigLocation", args[++i]);
            } else if (in(arg, "-f", "--filter")) {
                properties.put("objectFilter", removeSurroundingQuotes(args[++i]));
            } else if (in(arg, "-tf", "--type-filter")) {
                properties.put("typeFilter", args[++i]);
            } else if (in(arg, "-tfm", "--type-filtermode")) {
                properties.put("typeFilterMode", args[++i]);
            } else if (in(arg, "-v", "--version")) {
                properties.put("justPrintVersion", "true");
            } else if (arg.startsWith("-")) {
                throw new RuntimeException("Unknown argument: " + arg);
            }
        }

        return properties;
    }

}
