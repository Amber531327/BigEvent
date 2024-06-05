package amber.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Category {
    @NotNull(groups = {Update.class,delete.class})
    private Integer id;//主键ID
    @NotEmpty(groups = {Update.class, Add.class})
    private String categoryName;//分类名称
    @NotEmpty(groups = {Update.class, Add.class})
    private String categoryAlias;//分类别名
    private Integer createUser;//创建人ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间

    //如果某个校验项没有指定分组，默认属于Default分组
    //分组之间可以继承，A extends B,那么A中拥有B中所有的校验项
    public interface Add extends Default {
        //继承了default就可以把分类名称和分类别名上的groups直接去掉
    }
    public interface Update extends Default{

    }

    public interface delete extends Default {

    }
}
