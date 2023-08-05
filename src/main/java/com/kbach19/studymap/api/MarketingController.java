package com.kbach19.studymap.api;

import com.kbach19.studymap.api.dto.TopStudyMapsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/marketing")
public class MarketingController {

    @GetMapping("/top-study-maps")
    public ResponseEntity<TopStudyMapsResponse> getTopStudyMaps() {
        return ResponseEntity.ok(new TopStudyMapsResponse());
    }

}
