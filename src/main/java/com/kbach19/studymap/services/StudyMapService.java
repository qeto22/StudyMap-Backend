package com.kbach19.studymap.services;

import com.kbach19.studymap.api.dto.CreateStudyMapRequest;
import com.kbach19.studymap.model.StudyMap;
import com.kbach19.studymap.model.SystemUser;
import com.kbach19.studymap.utils.JsonUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StudyMapService {

    @Autowired
    private StudyMapRepository studyMapRepository;

    @Autowired
    private ImageService imageService;

    @SneakyThrows
    public Long create(CreateStudyMapRequest request, MultipartFile image) {
        String imageName = imageService.saveImage(image);

        SystemUser user = getAuthenticatedUser();

        String mapData = JsonUtils.getJsonString(request.getNodeData());

        StudyMap studyMap = StudyMap.builder()
                .author(user)
                .imagePath("/image/" + imageName)
                .title(request.getMapTitle())
                .description(request.getMapDescription())
                .mapData(mapData)
                .build();
        studyMap = studyMapRepository.save(studyMap);
        return studyMap.getId();
    }

    private SystemUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (SystemUser) authentication.getPrincipal();
    }

    public StudyMap getStudyMap(Long id) {
        return studyMapRepository.findById(id).orElse(null);
    }
}
