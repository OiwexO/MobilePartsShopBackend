package com.nulp.mobilepartsshop.core.entity.telemetry;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "telemetry_events")
public class TelemetryEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private EventType eventType;

    /**
     * ID of the user who triggered the event. Can be null for anonymous actions.
     */
    private Long userId;

    /**
     * Flexible JSON payload containing event-specific data
     * (e.g., {"partId": 15, "searchQuery": "display"}).
     */
    @Column(columnDefinition = "TEXT")
    private String payload;

    /**
     * The exact time the event occurred on the client device.
     */
    @Column(nullable = false)
    private LocalDateTime clientTimestamp;

    /**
     * The time the event was received and saved by the server.
     */
    @Column(nullable = false)
    private LocalDateTime receivedAt;

    /**
     * E.g., "CLIENT_APP", "STAFF_APP" to differentiate sources.
     */
    @Column(length = 50)
    private String sourceApp;
}