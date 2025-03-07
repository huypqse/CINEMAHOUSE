package cinemahouse.project.service.interfaces;

import cinemahouse.project.dto.response.IpnResponse;

import java.util.Map;

public interface IpnHandler {
    IpnResponse process(Map<String, String> params);
}
