package com.clx.clxdash.Device;

import com.clx.clxdash.jpa.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRespository extends JpaRepository<DeviceEntity, Integer> {
}
