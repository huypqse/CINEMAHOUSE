package cinemahouse.project.mapper;

import cinemahouse.project.dto.ScreeningSessionDTO;
import cinemahouse.project.entity.ScreeningSession;
import org.mapstruct.Mapper;

@Mapper(config = DefaultConfigMapper.class)
public interface ScreeningSessionMapper extends EntityMapper<ScreeningSessionDTO, ScreeningSession> {
}
