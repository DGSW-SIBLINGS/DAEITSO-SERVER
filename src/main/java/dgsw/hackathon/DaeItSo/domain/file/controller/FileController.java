package dgsw.hackathon.DaeItSo.domain.file.controller;

import dgsw.hackathon.DaeItSo.domain.file.dto.ImgUrlResponseDto;
import dgsw.hackathon.DaeItSo.domain.file.service.FileService;
import dgsw.hackathon.DaeItSo.global.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping
    public ResponseEntity<DataResponse<List<ImgUrlResponseDto>>> upload(@RequestPart List<MultipartFile> file) throws IOException {
        return DataResponse.ok("파일 업로드 성공", fileService.upload(file));
    }
}
