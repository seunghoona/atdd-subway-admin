package nextstep.subway.line.dto;

import static java.util.stream.Collectors.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nextstep.subway.line.domain.Line;
import nextstep.subway.section.domain.Section;
import nextstep.subway.station.dto.StationResponse;

public class LineResponse {
    private Long id;
    private String name;
    private String color;
    private List<StationResponse> stations;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static LineResponse of(Line line) {

        return new LineResponse(line.getId(), line.getName(), line.getColor(), line.getCreatedDate(),
                                line.getModifiedDate(), toStations(line)
        );
    }

    public static List<LineResponse> ofList(List<Line> findAll) {
        return findAll.stream()
                      .map(LineResponse::of)
                      .collect(toList());
    }

    private LineResponse(Long id, String name, String color, LocalDateTime createdDate, LocalDateTime modifiedDate,
        List<StationResponse> stations) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.stations = stations;
    }

    protected LineResponse() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<StationResponse> getStations() {
        return stations;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    private static List<StationResponse> toStations(Line line) {
        List<StationResponse> stations = new ArrayList<>();
        List<Section> sections = line.getSections();
        for (Section section : sections) {
            stations.add(StationResponse.of(section.getDownStationId()));
            stations.add(StationResponse.of(section.getUpStationId()));
        }
        Collections.sort(stations);
        return stations;
    }
}
