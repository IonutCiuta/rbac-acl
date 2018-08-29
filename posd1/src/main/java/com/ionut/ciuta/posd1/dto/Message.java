package com.ionut.ciuta.posd1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ionutciuta24@gmail.com on 02.11.2017.
 */
public class Message {
    @JsonProperty
    String text;

    public Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
