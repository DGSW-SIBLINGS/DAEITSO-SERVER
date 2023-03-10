package dgsw.hackathon.DaeItSo.domain.file.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import dgsw.hackathon.DaeItSo.domain.file.dto.ImgUrlResponseDto;
import dgsw.hackathon.DaeItSo.global.config.properties.S3Properties;
import dgsw.hackathon.DaeItSo.global.error.CustomError;
import dgsw.hackathon.DaeItSo.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {

    private final S3Properties s3Properties;
    private final AmazonS3Client s3Client;

    public List<ImgUrlResponseDto> upload(List<MultipartFile> files) throws IOException {

        List<ImgUrlResponseDto> imgUrlResponseDtoList = new ArrayList<>();

        for (MultipartFile file : files) {

            extensionCheck(file.getOriginalFilename());

            String ext = file.getOriginalFilename().split("\\.")[1];

            String s3FileName = UUID.randomUUID() + "." + ext;

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            s3Client.putObject(s3Properties.getBucket(), s3FileName, file.getInputStream(), metadata);

            imgUrlResponseDtoList.add(ImgUrlResponseDto.builder()
                    .imgUrl(s3Client.getUrl(s3Properties.getBucket(), s3FileName).toString()).build());
        }
        return imgUrlResponseDtoList;
    }

    private void extensionCheck(String imgUrl) {

        final String[] permitExtension = {".jpg", ".jpeg", ".png"};
        boolean check = false;

        for (String permit : permitExtension) {
            if(imgUrl.contains(permit)) {
                check = true;
                break;
            }
        }
        if (check == false) throw CustomError.of(ErrorCode.WRONG_FILE);
    }

    public void update(List<ImgUrlResponseDto> updateUrls, String originalUrls) {

        List<String> updateFileNameList = updateUrls.stream().map(imgUrlResponseDto -> imgUrlResponseDto.getImgUrl().split("m/")[1]).collect(Collectors.toList());
        List<String> originalFileNameList = List.of(originalUrls.split("///")).stream().map(url -> url.split("m/")[1]).collect(Collectors.toList());

        for (String originalFileName : originalFileNameList) {
            boolean checkUrl = false;
            for (String updateFileName : updateFileNameList) {
                if(originalFileName.equals(updateFileName)) {
                    checkUrl = true;
                    break;
                }
            }
            if (checkUrl == false) {
                s3Client.deleteObject(new DeleteObjectRequest(s3Properties.getBucket(), originalFileName));
            }
        }
    }

    public void delete(String imgUrl) {
        String fileName = imgUrl.split("m/")[1];
        s3Client.deleteObject(new DeleteObjectRequest(s3Properties.getBucket(), fileName));
    }
}
