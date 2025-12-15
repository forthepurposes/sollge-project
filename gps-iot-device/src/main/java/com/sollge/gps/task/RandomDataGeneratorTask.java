package com.sollge.gps.task;

import com.sollge.gps.mqtt.MqttSender;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class RandomDataGeneratorTask {

    private final MqttSender sender;

    @Scheduled(fixedRate = 3_000L)
    public void generateRandomGPS() {
        try {
            double latitude = ThreadLocalRandom.current().nextDouble(-90.0, 90.0);
            double longitude = ThreadLocalRandom.current().nextDouble(-180.0, 180.0);

            String timestamp = Instant.now().toString();
            String payload = String.format(
                    "{\"latitude\": %.6f, \"longitude\": %.6f, \"timestamp\": \"%s\"}",
                    latitude, longitude, timestamp
            );

            sender.send(payload);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
