package me.cchao.insomnia.api.bean.req;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import me.cchao.insomnia.api.util.SortHelper;

/**
 * @author : cchao
 * @version 2019-03-09
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class PageDTO {
    @Range(min = 1, message = "页码需大于1")
    int page = 1;
    @Range(min = 5, max = 100, message = "页大小为 5-100 之间")
    int pageSize = 10;

    public Pageable toPageable() {
        return PageRequest.of(page - 1, pageSize);
    }

    public Pageable toPageIdDesc() {
        return PageRequest.of(page - 1, pageSize, SortHelper.basicDownSort("id"));
    }

    public static PageDTO of(int page, int pageSize) {
        return new PageDTO(page, pageSize);
    }
}
