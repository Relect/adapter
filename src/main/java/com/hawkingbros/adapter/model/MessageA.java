package com.hawkingbros.adapter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageA {
    private String msg;
    private String lng;
    private Coordinates coordinates;
}
