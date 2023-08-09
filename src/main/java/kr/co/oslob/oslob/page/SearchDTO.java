package kr.co.oslob.oslob.page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class SearchDTO {

    private String[] types;
    private String keyword;

}
