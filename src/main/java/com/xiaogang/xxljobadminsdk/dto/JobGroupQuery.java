package com.xiaogang.xxljobadminsdk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : xiaogang
 * Created in 9:47 2023/1/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobGroupQuery implements Serializable {
    private int start = 0;
    private int length = 10;
    private String appname;
    private String title;
}
