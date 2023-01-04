package com.xiaogang.xxljobadminsdk.dto;

import com.xiaogang.xxljobadminsdk.constants.TriggerStatusEnum;
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
public class JobQuery implements Serializable {
    private int start = 0;
    private int length = 10;
    private int jobGroup;
    private TriggerStatusEnum triggerStatus = TriggerStatusEnum.ALL;
    private String jobDesc;
    private String executorHandler;
    private String author;
}
