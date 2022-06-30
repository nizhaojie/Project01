package org.sse.pojo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("generateplan")
public class GeneratePlan implements Serializable {
    @TableId(type = IdType.AUTO)
    private int id;
    private long productId;
    private int targetAmount;
    private int existingAmount;
    private String startTime;
    private String deadline;
    private long factoryId;
    private int status;
}
