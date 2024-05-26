package amber.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private Integer code;//业务状态码，0是成功，1是失败
    private String message;//提示信息
    private T data;//响应数据

    //快速返回操作成功响应结果（带响应数据）
    public static <T> Result<T> success(T data) {
        return new Result<>(0,"操作成功",data);
    }
    //快速返回操作成功响应结果
    public static Result success(){
        return new Result(0,"操作成功",null);
    }

    public static Result error(String message) {
        return new Result(1,message,null);
    }
}
