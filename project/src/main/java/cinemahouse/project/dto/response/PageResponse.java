package cinemahouse.project.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse<T> {
    int currentPage; // Trang hiện tại
    int totalPages; // Tổng số trang
    int pageSize;   // Số phần tử trên một trang
    long totalElements; // Tổng số phần tử

    @Builder.Default
    List<T> orders = Collections.emptyList(); // Sắp xếp (field -> direction)

    /**
     * Tạo Pageable từ thông tin phân trang và sắp xếp.
     */
//    public Pageable pageable() {
//        if (CollectionUtils.isEmpty(orders)) {
//            return PageRequest.of(currentPage - 1, pageSize);
//        }
//        Sort sort = sortable(orders);
//        return PageRequest.of(currentPage - 1, pageSize, sort);
//    }
//
//    /**
//     * Chuyển đổi danh sách Order thành đối tượng Sort.
//     */
//    public Sort sortable(List<T> orders) {
//        List<Sort.Order> sortableList = new ArrayList<>();
//        for (T order : orders) {
//            Sort.Direction direction = "DESC".equalsIgnoreCase(order.)
//                    ? Sort.Direction.DESC
//                    : Sort.Direction.ASC;
//            sortableList.add(new Sort.Order(direction, order.getField()));
//        }
//        return Sort.by(sortableList);
//    }
}
