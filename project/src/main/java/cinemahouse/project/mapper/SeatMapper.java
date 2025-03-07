package cinemahouse.project.mapper;

import cinemahouse.project.dto.SeatDTO;
import cinemahouse.project.entity.Seat;
import org.mapstruct.Mapper;

@Mapper(config = DefaultConfigMapper.class)
public interface SeatMapper  extends EntityMapper<SeatDTO, Seat> {
}
