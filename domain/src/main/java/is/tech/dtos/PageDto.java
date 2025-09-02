package is.tech.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PageDto<T> {
    private List<T> content;
    private int pageNumber;
    private int size;
    private long totalElements;

    public PageDto() {};

    public PageDto(Page<T> page) {
        content = page.getContent();
        pageNumber = page.getNumber();
        size = page.getSize();
        totalElements = page.getTotalElements();
    }
}
