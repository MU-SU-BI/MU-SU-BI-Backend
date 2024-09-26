package com.musubi.domain.location.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NaverMapApiResponseDto {

    private Status status;
    private List<Result> results;

    public String getDistrict() {
        return results.get(0).getRegion().areaNameToDistrict();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static private class Status {
        private String name;
        private String message;
        private int code;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Result {
        private String name;
        private Code code;
        private Region region;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        static class Code {
            private String id;
            private String type;
            private String mappingId;
        }

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        static class Region {

            private Area area0;
            private Area area1;
            private Area area2;
            private Area area3;
            private Area area4;

            public String areaNameToDistrict() {
                return area1.getName()
                        + " "
                        + area2.getName()
                        + " "
                        + area3.getName();
            }

            @Getter
            @NoArgsConstructor
            @AllArgsConstructor
            static class Area {
                private String name;
                private Coords coords;
                private String alias;


                @Getter
                @NoArgsConstructor
                @AllArgsConstructor
                static class Coords {
                    private Center center;


                    @Getter
                    @NoArgsConstructor
                    @AllArgsConstructor
                    static class Center {
                        String crs;
                        double x;
                        double y;
                    }
                }
            }
        }
    }
}
