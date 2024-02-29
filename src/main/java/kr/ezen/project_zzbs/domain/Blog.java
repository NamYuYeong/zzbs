package kr.ezen.project_zzbs.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Blog {
    private String btitle;
    private String bdescription;
    private String url;
}
