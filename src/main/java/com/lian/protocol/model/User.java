package com.lian.protocol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/6/2 21:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class User implements Serializable {
    private Long id;
    private String name;
    private String password;
    private String phone;
    private Long createTime;
}
