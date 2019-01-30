package com.cchao.sleep.json.fall;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author : cchao
 * @version 2019-01-30
 */
@Data
public class FallImageVo {
    private Long id;

    private String url;

    private Integer width;

    private Integer height;
}
