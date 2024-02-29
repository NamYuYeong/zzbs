package kr.ezen.project_zzbs.repository;

import kr.ezen.project_zzbs.domain.UploadImage;

import java.math.BigInteger;

public interface BoardInterface {
    Long getId();
    String getTitle();
    String getBody();
    String getRating();
    BigInteger getUpload_Image_id();
    String getSaved_filename();
}