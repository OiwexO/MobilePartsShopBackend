package com.nulp.mobilepartsshop.core.repository.part;

import com.nulp.mobilepartsshop.core.entity.part.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartRecommendationRepository extends JpaRepository<Part, Long> {

    /**
     * Custom query method to find parts based on optional criteria such as manufacturer, device type, and part type,
     * and order the results by price in ascending order.
     *
     * @param manufacturerId The ID of the manufacturer to filter parts. Can be 0 to indicate no filter.
     * @param deviceTypeId   The ID of the device type to filter parts. Can be 0 to indicate no filter.
     * @param partTypeId     The ID of the part type to filter parts. Can be 0 to indicate no filter.
     * @return A list of Part entities that match the specified criteria, ordered by price in ascending order.
     */
    @Query("SELECT p FROM Part p WHERE (:manufacturerId = 0 OR p.manufacturer.id = :manufacturerId) "
            + "AND (:deviceTypeId = 0 OR p.deviceType.id = :deviceTypeId) "
            + "AND (:partTypeId = 0 OR p.partType.id = :partTypeId) "
            + "ORDER BY p.price ASC")
    List<Part> findByManufacturerIdAndDeviceTypeIdAndPartTypeIdOrderByPriceAsc(@Param("manufacturerId") Long manufacturerId,
                                                                               @Param("deviceTypeId") Long deviceTypeId,
                                                                               @Param("partTypeId") Long partTypeId);

    /**
     * Custom query method to find parts based on optional criteria such as manufacturer, device type, and part type,
     * and order the results by price in descending order.
     *
     * @param manufacturerId The ID of the manufacturer to filter parts. Can be 0 to indicate no filter.
     * @param deviceTypeId   The ID of the device type to filter parts. Can be 0 to indicate no filter.
     * @param partTypeId     The ID of the part type to filter parts. Can be 0 to indicate no filter.
     * @return A list of Part entities that match the specified criteria, ordered by price in descending order.
     */
    @Query("SELECT p FROM Part p WHERE (:manufacturerId = 0 OR p.manufacturer.id = :manufacturerId) "
            + "AND (:deviceTypeId = 0 OR p.deviceType.id = :deviceTypeId) "
            + "AND (:partTypeId = 0 OR p.partType.id = :partTypeId) "
            + "ORDER BY p.price DESC")
    List<Part> findByManufacturerIdAndDeviceTypeIdAndPartTypeIdOrderByPriceDesc(@Param("manufacturerId") Long manufacturerId,
                                                                                @Param("deviceTypeId") Long deviceTypeId,
                                                                                @Param("partTypeId") Long partTypeId);

    /**
     * Custom query method to find parts based on criteria such as manufacturer, device type, and device model,
     * and order the results by price in ascending order.
     *
     * @param manufacturerId The ID of the manufacturer to filter parts.
     * @param deviceTypeId   The ID of the device type to filter parts.
     * @param deviceModel    The device model to filter parts.
     * @return A list of Part entities that match the specified criteria, ordered by price in ascending order.
     */
    @Query("SELECT p FROM Part p WHERE (p.manufacturer.id = :manufacturerId) " +
            "AND (p.deviceType.id = :deviceTypeId) " +
            "AND (p.deviceModels IS EMPTY OR :deviceModel MEMBER OF p.deviceModels) " +
            "ORDER BY p.price ASC")
    List<Part> findByManufacturerIdAndDeviceTypeIdAndDeviceModelsContainingIgnoreCaseOrderByPriceAsc(
            @Param("manufacturerId") Long manufacturerId,
            @Param("deviceTypeId") Long deviceTypeId,
            @Param("deviceModel") String deviceModel);

    /**
     * Custom query method to find parts based on criteria such as manufacturer, device type, and device model,
     * and order the results by price in descending order.
     *
     * @param manufacturerId The ID of the manufacturer to filter parts.
     * @param deviceTypeId   The ID of the device type to filter parts.
     * @param deviceModel    The device model to filter parts.
     * @return A list of Part entities that match the specified criteria, ordered by price in descending order.
     */
    @Query("SELECT p FROM Part p WHERE (p.manufacturer.id = :manufacturerId) " +
            "AND (p.deviceType.id = :deviceTypeId) " +
            "AND (p.deviceModels IS EMPTY OR :deviceModel MEMBER OF p.deviceModels) " +
            "ORDER BY p.price DESC")
    List<Part> findByManufacturerIdAndDeviceTypeIdAndDeviceModelsContainingIgnoreCaseOrderByPriceDesc(
            @Param("manufacturerId") Long manufacturerId,
            @Param("deviceTypeId") Long deviceTypeId,
            @Param("deviceModel") String deviceModel);
}
