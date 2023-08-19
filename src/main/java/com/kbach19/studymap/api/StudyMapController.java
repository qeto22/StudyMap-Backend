package com.kbach19.studymap.api;

import com.kbach19.studymap.api.dto.Author;
import com.kbach19.studymap.api.dto.CreateStudyMapRequest;
import com.kbach19.studymap.api.dto.CreateStudyMapResponse;
import com.kbach19.studymap.api.dto.GetStudyMapResponse;
import com.kbach19.studymap.model.StudyMap;
import com.kbach19.studymap.services.StudyMapService;
import com.kbach19.studymap.utils.JsonUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/map")
public class StudyMapController {

    @Autowired
    private StudyMapService studyMapService;

    @SneakyThrows
    @GetMapping("/{id}")
    public ResponseEntity<GetStudyMapResponse> getStudyMap(@PathVariable Long id) {
        StudyMap studyMap = studyMapService.getStudyMap(id);
        if (studyMap == null) {
            return ResponseEntity.badRequest()
                    .build();
        }

        Author author = Author.builder()
                .name(studyMap.getAuthor().getFirstName() + " " + studyMap.getAuthor().getLastName())
                .username(studyMap.getAuthor().getUsername())
//                .description(studyMap.getAuthor().get)
                .build();

        return ResponseEntity.ok(GetStudyMapResponse.builder()
                .mapTitle(studyMap.getTitle())
                .mapDescription(studyMap.getDescription())
                .nodeData(JsonUtils.getJsonNode(studyMap.getMapData()))
                .author(author)
                .build());
    }

    @PostMapping("/create")
    public ResponseEntity<CreateStudyMapResponse> createStudyMap(@RequestBody CreateStudyMapRequest request) {
        return ResponseEntity.ok(CreateStudyMapResponse.builder()
                .id(studyMapService.create(request))
                .build());
    }

}
