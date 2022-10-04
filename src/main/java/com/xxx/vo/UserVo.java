package com.xxx.vo;

import lombok.Data;

@Data
public class UserVo {
    private Integer id;
    private String name;
    private String avatar;
    private String description;
    private Integer followCount;
    private Integer fansCount;
}
