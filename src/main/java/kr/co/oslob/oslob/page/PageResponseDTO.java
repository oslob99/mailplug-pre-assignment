package kr.co.oslob.oslob.page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

@Setter
@Getter
@ToString
public class PageResponseDTO<T> {

    private int startPage;
    private int endPage;
    private int currentPage;


    private boolean prev;
    private boolean next;

    private int total;

    private static final int PAGE_COUNT = 5;

    public PageResponseDTO(Page<T> pageData) {
        this.total = (int) pageData.getTotalElements();
        this.currentPage = pageData.getPageable().getPageNumber() + 1;
        this.endPage = (int) (Math.ceil((double) currentPage / PAGE_COUNT) * PAGE_COUNT);
        this.startPage = endPage - PAGE_COUNT + 1;

        int realEnd = pageData.getTotalPages();

        if (realEnd < this.endPage) this.endPage = realEnd;

        this.prev = startPage > 1;
        this.next = endPage < realEnd;

    }
}