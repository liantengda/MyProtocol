package com.lian.protocol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/6/2 21:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {

    private Long id;

    private String name;

    private String url;

    private Long createTime;

}
