package com.lecuong.sourcebase.controller.redis;

import com.lecuong.sourcebase.exception.BusinessException;
import com.lecuong.sourcebase.exception.StatusTemplate;
import com.lecuong.sourcebase.modal.response.BaseResponse;
import com.lecuong.sourcebase.repository.redis.SessionRepository;
import com.lecuong.sourcebase.util.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/redis")
public class RedisController {

    private static final String KEY_SESSION_ID_LIST = "session-id-list";
    private static final String KEY_SESSION_ID = "session-id";

    @Autowired
    private SessionRepository sessionRepository;

    @PostMapping("/add-list-session-id")
    public ResponseEntity<BaseResponse<Void>> addListToRedis() {
        List<String> sessionIdList = List.of(
                "0649a5f7-ac6f-43e0-b846-40fc76db9973",
                "08c30d7f-4e79-47e0-8412-4c3eda40dfcb",
                "4a1d4e3a-a142-4f43-a628-a9e9ad0e54a7",
                "85f0cb11-8344-46ba-a4ea-e0379e2f95bc"
        );
        sessionRepository.addList(KEY_SESSION_ID_LIST, sessionIdList);
        return ResponseEntity.ok(BaseResponse.ofSuccess(null));
    }

    @PostMapping("/add-session-id")
    public ResponseEntity<BaseResponse<Void>> addRedis() {
        sessionRepository.add(KEY_SESSION_ID, "0649a5f7-ac6f-43e0-b846-40fc76db9973");
        return ResponseEntity.ok(BaseResponse.ofSuccess(null));
    }

    @GetMapping("/get-list-session-id")
    public ResponseEntity<BaseResponse<List<String>>> findListInRedis() {
        List<String> sessionIds = sessionRepository.findList(KEY_SESSION_ID_LIST);
        if (DataUtils.isNullOrEmpty(sessionIds)) {
            throw new BusinessException(StatusTemplate.SESSION_ID_EMPTY);
        }
        return ResponseEntity.ok(BaseResponse.ofSuccess(sessionIds));
    }

    @GetMapping("/get-session-id")
    public ResponseEntity<BaseResponse<String>> findInRedis() {
        String sessionId = sessionRepository.find(KEY_SESSION_ID);
        if (DataUtils.isTrue(sessionId)) {
            throw new BusinessException(StatusTemplate.SESSION_ID_EMPTY);
        }
        return ResponseEntity.ok(BaseResponse.ofSuccess(sessionId));
    }

    @DeleteMapping("/delete-list-session-id/{key}")
    public ResponseEntity<BaseResponse<Void>> deleteRedis(@PathVariable String key) {
        List<String> sessionIds = sessionRepository.findList(key);
        if (DataUtils.isTrue(sessionIds)) {
            throw new BusinessException(StatusTemplate.SESSION_ID_EMPTY);
        }
        sessionRepository.delete(key);
        return ResponseEntity.ok(BaseResponse.ofSuccess(null));
    }

    @DeleteMapping("/delete-session-id/{key}")
    public ResponseEntity<BaseResponse<Void>> deleteSessionId(@PathVariable String key) {
        String sessionId = sessionRepository.find(KEY_SESSION_ID);
        if (DataUtils.isNullOrEmpty(sessionId)) {
            throw new BusinessException(StatusTemplate.SESSION_ID_EMPTY);
        }
        sessionRepository.delete(key);
        return ResponseEntity.ok(BaseResponse.ofSuccess(null));
    }
}
