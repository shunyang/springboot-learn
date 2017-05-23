package com.yangyang.springboot.example.autoconfigure;

/**
 * Created by chenshunyang on 2017/5/23.
 */
public class ExampleService {
    private String prefix;
    private String suffix;

    public ExampleService(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }
    public String wrap(String word) {
        return prefix + word + suffix;
    }
}
