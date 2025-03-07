package cinemahouse.project.mapper;

import cinemahouse.project.dto.MovieTypeDTO;
import cinemahouse.project.entity.MovieType;
import org.mapstruct.Mapper;

@Mapper(config = DefaultConfigMapper.class)
public interface MovieTypeMapper extends EntityMapper<MovieTypeDTO, MovieType> {

}
