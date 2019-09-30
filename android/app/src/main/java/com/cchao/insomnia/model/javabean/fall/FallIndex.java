package com.cchao.insomnia.model.javabean.fall;

import java.util.List;

import lombok.Data;

@Data
public class FallIndex {
    List<FallImage> fallImages;
    List<FallMusic> music;
}