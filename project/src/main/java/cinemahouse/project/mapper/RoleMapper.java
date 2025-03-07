package cinemahouse.project.mapper;

import cinemahouse.project.dto.RoleDTO;
import cinemahouse.project.entity.Role;
import org.mapstruct.Mapper;

@Mapper(config = DefaultConfigMapper.class)
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {

}
