package com.lecuong.sourcebase.controller.user;

import com.lecuong.sourcebase.common.DateTimeCommon;
import com.lecuong.sourcebase.constant.TemplateReportConstant;
import com.lecuong.sourcebase.controller.BaseController;
import com.lecuong.sourcebase.modal.request.user.UserFilterRequest;
import com.lecuong.sourcebase.modal.request.user.UserFilterWithListBlogRequest;
import com.lecuong.sourcebase.modal.request.user.UserSaveRequest;
import com.lecuong.sourcebase.modal.request.user.UserUpdateRequest;
import com.lecuong.sourcebase.modal.response.BaseResponse;
import com.lecuong.sourcebase.modal.response.user.UserResponse;
import com.lecuong.sourcebase.service.ReportService;
import com.lecuong.sourcebase.service.UserService;
import lombok.Data;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/user")
public class UserController extends BaseController {

    private final String FILE_NAME = "author_report_%s.xlsx";

    private final ReportService reportService;
    private final UserService userService;

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> update(@PathVariable Long id,
                                                     @RequestBody UserUpdateRequest userUpdateRequest) {
        userService.update(id, userUpdateRequest);
        return ResponseEntity.ok(BaseResponse.ofSuccess(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(BaseResponse.ofSuccess(null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<UserResponse>> findById(@PathVariable Long id) {
        UserResponse userResponse =userService.findById(id);
        return ResponseEntity.ok(BaseResponse.ofSuccess(userResponse));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BaseResponse<Page<UserResponse>>> getAll(@RequestParam int pageIndex,
                                                                   @RequestParam int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
        Page<UserResponse> userResponses = userService.getAll(pageRequest.previousOrFirst());
        return ResponseEntity.ok(BaseResponse.ofSuccess(userResponses));
    }

    @GetMapping("/filter")
    public ResponseEntity<BaseResponse<Page<UserResponse>>> filter(@ModelAttribute UserFilterRequest userFilterRequest) {
        Page<UserResponse> userResponses = userService.filter(userFilterRequest);
        return ResponseEntity.ok(BaseResponse.ofSuccess(userResponses));
    }

    @PostMapping("/filter/user-by-blog")
    public ResponseEntity<BaseResponse<Page<UserResponse>>> filter(@RequestBody UserFilterWithListBlogRequest userFilterWithListBlogRequest) {
        Page<UserResponse> userResponses = userService.filter(userFilterWithListBlogRequest);
        return ResponseEntity.ok(BaseResponse.ofSuccess(userResponses));
    }

    @PostMapping("/message")
    public ResponseEntity<BaseResponse<String>> getMessage(@RequestBody UserSaveRequest userRequest) {
        return ResponseEntity.ok(BaseResponse.ofSuccess(userService.getMessage(userRequest)));
    }

    @GetMapping("/find-by-id-and-call-proc/{id}")
    public ResponseEntity<BaseResponse<UserResponse>> findByIdAndCallProc(@PathVariable Long id) {
        UserResponse userResponse =userService.findUserByIdAndCallProc(id);
        return ResponseEntity.ok(BaseResponse.ofSuccess(userResponse));
    }
}
