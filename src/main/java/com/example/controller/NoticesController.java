package com.example.controller;

import com.example.model.Notice;
import com.example.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
public class NoticesController {

    private final NoticeRepository noticeRepository;

    //@GetMapping("/notices")
    //public String getNotices() {
    //
    //    return "Here are the notices from the DB";
    //}

    @GetMapping("/notices")
    public ResponseEntity<List<Notice>> getNotices() {

        List<Notice> notices = (List<Notice>) noticeRepository.findAll();

        if (notices != null) {
            return ResponseEntity.ok()
                    .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                    .body(notices);
        }

        return null;
    }
}
