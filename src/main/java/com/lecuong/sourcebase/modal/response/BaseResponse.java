package com.lecuong.sourcebase.modal.response;

import com.lecuong.sourcebase.exception.BusinessException;
import com.lecuong.sourcebase.exception.StatusResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BaseResponse<T> {

    private HttpStatus code;
    private T data;

    public BaseResponse(HttpStatus code, T data) {
        this.code = code;
        this.data = data;
    }

    public static <M> BaseResponse<M> ofSuccess(M data) {
        return new BaseResponse<>(HttpStatus.OK, data);
    }

    public static BaseResponse<StatusResponse> ofFail(BusinessException businessException) {
        return new BaseResponse<>(businessException.getStatusResponse().getStatus(), businessException.getStatusResponse());
    }
}
