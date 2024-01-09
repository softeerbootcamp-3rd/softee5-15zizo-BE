package com.zizo.carteeng.members.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

@Getter
public class LatLng {

    @NotNull(message = "위도를 입력해주세요.")
    private Double lat;

    @NotNull(message = "경도를 입력해주세요.")
    private Double lng;

    private LatLng(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Point toPoint() {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        return geometryFactory.createPoint(new Coordinate(lat, lng));
    }

    static public LatLng fromPoint(Point point) {
        return new LatLng(point.getX(), point.getY());
    }
}
