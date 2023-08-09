package kr.co.oslob.oslob.page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class PageDTO {

    private int offset;
    private int limit;

    public PageDTO(){
        this.offset = 1;
        this.limit = 10;
    }
}
