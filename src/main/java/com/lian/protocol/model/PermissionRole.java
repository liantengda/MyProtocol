package com.lian.protocol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/6/2 21:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionRole {
    private Long id;
    private Long roleId;
    private Long permissionId;
    private Long createTime;
}
