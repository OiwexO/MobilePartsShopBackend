package com.nulp.mobilepartsshop.core.service.telemetry;

import com.nulp.mobilepartsshop.api.v1.analytics.dto.AnalyticsDashboardResponse;
import com.nulp.mobilepartsshop.api.v1.part.service.PartService;
import com.nulp.mobilepartsshop.api.v1.telemetry.dto.request.TelemetryBatchRequest;
import com.nulp.mobilepartsshop.api.v1.telemetry.dto.request.TelemetryEventRequest;
import com.nulp.mobilepartsshop.core.entity.part.DeviceType;
import com.nulp.mobilepartsshop.core.entity.part.Part;
import com.nulp.mobilepartsshop.core.entity.part.PartType;
import com.nulp.mobilepartsshop.core.entity.part.manufacturer.Manufacturer;
import com.nulp.mobilepartsshop.core.entity.telemetry.EventType;
import com.nulp.mobilepartsshop.core.entity.telemetry.TelemetryEvent;
import com.nulp.mobilepartsshop.core.repository.telemetry.TelemetryEventRepository;
import com.nulp.mobilepartsshop.core.repository.telemetry.projection.AnalyticsProjections.*;
import com.nulp.mobilepartsshop.core.service.analytics.AnalyticsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnalyticsAndTelemetryServicesTest {

    @Mock
    private TelemetryEventRepository telemetryRepository;

    @Mock
    private PartService partService;

    @InjectMocks
    private TelemetryServiceImpl telemetryService;

    @InjectMocks
    private AnalyticsServiceImpl analyticsService;

    private TelemetryBatchRequest batchRequest;

    @BeforeEach
    void setUp() {
        TelemetryEventRequest eventRequest = new TelemetryEventRequest();
        eventRequest.setEventType(EventType.PART_VIEWED);
        eventRequest.setUserId(1L);
        eventRequest.setPayload("{\"partId\": 10}");
        eventRequest.setClientTimestamp(LocalDateTime.now());
        eventRequest.setSourceApp("customer_application");

        batchRequest = new TelemetryBatchRequest();
        batchRequest.setEvents(List.of(eventRequest));
    }

    // --- Тести для TelemetryServiceImpl ---

    @Test
    void test1_processBatch_mapsAndSavesEventsCorrectly() {
        telemetryService.processBatch(batchRequest);

        ArgumentCaptor<List<TelemetryEvent>> captor = ArgumentCaptor.forClass(List.class);
        verify(telemetryRepository, times(1)).saveAll(captor.capture());

        List<TelemetryEvent> savedEvents = captor.getValue();
        assertEquals(1, savedEvents.size());
        assertEquals(EventType.PART_VIEWED, savedEvents.get(0).getEventType());
        assertEquals("customer_application", savedEvents.get(0).getSourceApp());
        assertNotNull(savedEvents.get(0).getReceivedAt());
    }

    // --- Тести для AnalyticsServiceImpl ---

    @Test
    void test2_getDashboard_handlesNullProjectionsGracefully() {
        // Усі репозиторії повертають null (пуста БД)
        when(telemetryRepository.getFinancialMetricsSince(any())).thenReturn(null);
        when(telemetryRepository.getSessionMetricsSince(any(), anyString(), anyString())).thenReturn(null);
        when(telemetryRepository.getCartMetricsSince(any())).thenReturn(null);
        when(telemetryRepository.countAppErrorsSince(any())).thenReturn(null);

        AnalyticsDashboardResponse response = analyticsService.getDashboard(30);

        assertEquals(0, response.getFinancialMetrics().getTotalOrders());
        assertEquals(0.0, response.getFinancialMetrics().getTotalRevenue());
        assertEquals(0.0, response.getEngagementMetrics().getAvgCustomerSessionSeconds());
        assertEquals(0, response.getSystemHealth().getAppErrorCount());
    }

    @Test
    void test3_getDashboard_mapsFinancialMetricsCorrectly() {
        FinancialProjection mockProjection = mock(FinancialProjection.class);
        when(mockProjection.getTotalOrders()).thenReturn(150);
        when(mockProjection.getTotalRevenue()).thenReturn(4500.50);
        when(mockProjection.getCanceledOrders()).thenReturn(5);

        when(telemetryRepository.getFinancialMetricsSince(any())).thenReturn(mockProjection);

        AnalyticsDashboardResponse response = analyticsService.getDashboard(7);

        assertEquals(150, response.getFinancialMetrics().getTotalOrders());
        assertEquals(4500.50, response.getFinancialMetrics().getTotalRevenue());
        assertEquals(5, response.getFinancialMetrics().getCanceledOrders());
    }

    @Test
    void test4_getDashboard_mapsSessionMetricsCorrectly() {
        SessionProjection mockProjection = mock(SessionProjection.class);
        when(mockProjection.getAvgCustomerSession()).thenReturn(120.5);
        when(mockProjection.getAvgStaffSession()).thenReturn(3600.0);

        when(telemetryRepository.getSessionMetricsSince(any(), eq("customer_application"), eq("staff_application")))
                .thenReturn(mockProjection);

        AnalyticsDashboardResponse response = analyticsService.getDashboard(30);

        assertEquals(120.5, response.getEngagementMetrics().getAvgCustomerSessionSeconds());
        assertEquals(3600.0, response.getEngagementMetrics().getAvgStaffSessionSeconds());
    }

    @Test
    void test5_getDashboard_mapsCartMetricsAndSystemHealth() {
        CartMetricsProjection cartMock = mock(CartMetricsProjection.class);
        when(cartMock.getItemsAdded()).thenReturn(50);
        when(cartMock.getItemsRemoved()).thenReturn(10);
        when(telemetryRepository.getCartMetricsSince(any())).thenReturn(cartMock);
        when(telemetryRepository.countAppErrorsSince(any())).thenReturn(3);

        AnalyticsDashboardResponse response = analyticsService.getDashboard(7);

        assertEquals(50, response.getCartMetrics().getItemsAdded());
        assertEquals(10, response.getCartMetrics().getItemsRemoved());
        assertEquals(3, response.getSystemHealth().getAppErrorCount());
    }

    @Test
    void test6_getDashboard_mapsTopViewedPartsSuccessfully() {
        PartCountProjection partMock = mock(PartCountProjection.class);
        when(partMock.getPartId()).thenReturn(1L);
        when(partMock.getEventCount()).thenReturn(100);

        var mockManufacturer = new Manufacturer();
        mockManufacturer.setId(1L);
        mockManufacturer.setName("Apple");

        var mockPartType = new PartType();
        mockPartType.setId(1L);
        mockPartType.setNameEn("Display");
        mockPartType.setNameUk("Дисплей");

        var mockDeviceType = new DeviceType();
        mockDeviceType.setId(1L);
        mockDeviceType.setNameEn("Smartphone");
        mockDeviceType.setNameUk("Смартфон");

        Part dummyPart = new Part();
        dummyPart.setId(1L);
        dummyPart.setName("Screen");
        dummyPart.setManufacturer(mockManufacturer);
        dummyPart.setPartType(mockPartType);
        dummyPart.setDeviceType(mockDeviceType);
        dummyPart.setDeviceModels(Collections.emptyList());

        when(telemetryRepository.getTopViewedPartsSince(any(), anyInt())).thenReturn(List.of(partMock));
        when(partService.getPartById(1L)).thenReturn(Optional.of(dummyPart));

        AnalyticsDashboardResponse response = analyticsService.getDashboard(30);

        assertEquals(1, response.getTopViewedParts().size());
        assertEquals(100, response.getTopViewedParts().get(0).getCount());
    }

    @Test
    void test7_getDashboard_ignoresPartsNotFoundInDatabase() {
        PartCountProjection partMock = mock(PartCountProjection.class);
        when(partMock.getPartId()).thenReturn(99L); // Неіснуюча деталь

        when(telemetryRepository.getTopViewedPartsSince(any(), anyInt())).thenReturn(List.of(partMock));
        // Імітуємо ситуацію, коли деталь була видалена з бази
        when(partService.getPartById(99L)).thenReturn(Optional.empty());

        AnalyticsDashboardResponse response = analyticsService.getDashboard(30);

        assertTrue(response.getTopViewedParts().isEmpty()); // Має відфільтрувати null
    }

    @Test
    void test8_getDashboard_handlesNullPartIdSafely() {
        PartCountProjection partMock = mock(PartCountProjection.class);
        when(partMock.getPartId()).thenReturn(null); // Якщо JSON не містив partId

        when(telemetryRepository.getTopPurchasedPartsSince(any(), anyInt())).thenReturn(List.of(partMock));

        AnalyticsDashboardResponse response = analyticsService.getDashboard(30);

        assertTrue(response.getTopPurchasedParts().isEmpty());
        verify(partService, never()).getPartById(anyLong()); // Сервіс не повинен викликатися
    }

    @Test
    void test9_getDashboard_mapsUnmetDemandCorrectly() {
        UnmetDemandProjection demandMock = mock(UnmetDemandProjection.class);
        when(demandMock.getSearchCriteria()).thenReturn("{\"query\":\"rtx 4090\"}");
        when(demandMock.getSearchCount()).thenReturn(25);

        when(telemetryRepository.getTopUnmetDemandSince(any(), anyInt())).thenReturn(List.of(demandMock));

        AnalyticsDashboardResponse response = analyticsService.getDashboard(30);

        assertEquals(1, response.getUnmetDemand().size());
        assertEquals("{\"query\":\"rtx 4090\"}", response.getUnmetDemand().get(0).getSearchCriteria());
        assertEquals(25, response.getUnmetDemand().get(0).getSearchCount());
    }

    @Test
    void test10_getDashboard_calculatesStartDateCorrectly() {
        int daysToSubtract = 14;
        analyticsService.getDashboard(daysToSubtract);

        ArgumentCaptor<LocalDateTime> dateCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        verify(telemetryRepository).getFinancialMetricsSince(dateCaptor.capture());

        LocalDateTime passedDate = dateCaptor.getValue();
        LocalDateTime expectedDate = LocalDateTime.now().minusDays(daysToSubtract);

        // Перевіряємо, що дати збігаються (з похибкою на мілісекунди виконання)
        assertTrue(passedDate.isBefore(expectedDate.plusSeconds(1)));
        assertTrue(passedDate.isAfter(expectedDate.minusSeconds(1)));
    }
}