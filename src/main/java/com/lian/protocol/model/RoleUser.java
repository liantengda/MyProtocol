package com.lian.protocol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/6/2 21:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleUser {
    private Long id;
    private Long userId;
    private Long roleId;
    private Long createTime;
}
