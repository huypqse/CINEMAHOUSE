package cinemahouse.project.mapper;

import cinemahouse.project.dto.BillDTO;
import cinemahouse.project.entity.Bill;
import org.mapstruct.Mapper;

@Mapper(config = DefaultConfigMapper.class)
public interface BillMapper extends EntityMapper<BillDTO , Bill> {

}
