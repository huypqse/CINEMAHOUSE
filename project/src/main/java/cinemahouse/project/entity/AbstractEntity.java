package cinemahouse.project.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
@Getter
@Setter
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AbstractEntity<T extends Serializable> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    T id;

    @Column(name = "created_by")
    @CreatedBy
    String createdBy;

    @Column(name = "updated_by")
    @LastModifiedBy
    String updateBy;

////    @Field(type = FieldType.Date, format = {}, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS'Z'")
//    @Column(name = "create_at")
////    @CreationTimestamp
//    LocalDate createdAt;
//
////    @Field(type = FieldType.Date, format = {}, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS'Z'")
//    @Column(name = "update_at")
////    @UpdateTimestamp
//    LocalDate updatedAt;

}
// @MappedSuperclass trong Spring (đặc biệt là trong JPA/Hibernate)
// được sử dụng để định nghĩa một lớp cha chứa các trường (fields)
// và mapping (liên kết) chung cho các lớp entity khác
