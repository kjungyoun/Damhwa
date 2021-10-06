<template>
  <div>
    <div id="map"></div>
    <div class="button-group">
      <button @click="displayInfoWindow">현재 위치</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "KakaoMap",
  data() {
    return {
      map: null,
      infowindow: null,
      latitude: "",
      longitude: "",
      geocoder: "",
      address: "",
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=e75f132cf57e43836d8d31100a80ad78";
      document.head.appendChild(script);
    }
  },
  methods: {
    initMap() {
      const container = document.getElementById("map");
      navigator.geolocation.getCurrentPosition((pos) => {
        this.latitude = pos.coords.latitude;
        this.longitude = pos.coords.longitude;
        const options = {
          center: new kakao.maps.LatLng(this.latitude, this.longitude),
          level: 5,
        };
        this.map = new kakao.maps.Map(container, options);
        this.geocoder = new kakao.maps.services.Geocoder();
        this.searchAddrFromCoords(this.map.getCenter(), this.displayCenterInfo);
      });
    },
    displayMarker(place) {
      var marker = new kakao.maps.Marker({
        map: this.map,
        position: new kakao.maps.LatLng(place.y, place.x),
      });
      // console.log(marker);

      // 마커에 클릭이벤트를 등록합니다
      kakao.maps.event.addListener(marker, 'click', function() {
          // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
          this.displayInfoWindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
          this.displayInfoWindow.open(this.map, marker);
      });
    },
    displayInfoWindow() {
      if (this.infowindow && this.infowindow.getMap()) {
        //이미 생성한 인포윈도우가 있기 때문에 지도 중심좌표를 인포윈도우 좌표로 이동시킨다.
        this.map.setCenter(this.infowindow.getPosition());
        return;
      }

      var iwContent = '<div style="padding:5px;">현재 위치</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
        iwPosition = new kakao.maps.LatLng(this.latitude, this.longitude), //인포윈도우 표시 위치입니다
        iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

      this.infowindow = new kakao.maps.InfoWindow({
        map: this.map, // 인포윈도우가 표시될 지도
        position: iwPosition,
        content: iwContent,
        removable: iwRemoveable,
      });

      this.map.setCenter(iwPosition);
    },
    searchAddrFromCoords(coords, callback) {
      this.geocoder.coord2RegionCode(
        coords.getLng(),
        coords.getLat(),
        callback
      );
    },
    displayCenterInfo(result, status) {
      if (status == "OK") {
        this.address = result[0].address_name; 
        const kw = this.address + " 꽃집";
        this.initPlaces(kw);
      }
    },
    initPlaces(kw) {
      let ps = new kakao.maps.services.Places();
      ps.keywordSearch(kw, this.placesSearchCB);
    },
    placesSearchCB(data, status, pagination) {
      if (status === "OK") {
        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        var bounds = new kakao.maps.LatLngBounds();

        for (var i = 0; i < data.length; i++) {
          this.displayMarker(data[i]);
          bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
        }

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
        this.map.setBounds(bounds);
        // console.log(pagination);
        pagination
        this.$emit("stores", data)
      }
    },
  },
};
</script>

<style scoped>
#map {
  width: 100%;
  height: 35vh;
}

.button-group {
  margin: 10px 0px;
}

button {
  margin: 0 3px;
}
</style>
