package cinemahouse.project.mapper;

import cinemahouse.project.dto.request.InitPaymentRequest;
import cinemahouse.project.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(config = DefaultConfigMapper.class)
public interface PaymentMapper extends EntityMapper<InitPaymentRequest, Payment> {

}
