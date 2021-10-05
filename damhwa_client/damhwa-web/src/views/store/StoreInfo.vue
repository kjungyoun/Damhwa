<template>
  <div>
    <StoreInfoKaKaoMap
    :Data="getStoreInfor"
    />
  </div>
</template>
<script>
import StoreInfoKaKaoMap from "@/views/store/components/StoreInfoKakaoMap"
// import { onMounted, reactive } from 'vue'
export default {
  name: "StoreeInfo",
  components: {
    StoreInfoKaKaoMap
  },
  setup() {
  //   const tokenSave = function(token) {
  //     console.log("ANDROID Main : "+token);

  // }
  // // 기본 데이터를 일단 카카오 본사 좌표값으로 지정했습니다.
  // const data = reactive({
  //   Lat: 33.450701,
  //   Lng: 126.570667
  // })
  // // 여기서 유저 위치 정보 받기
  // onMounted(() => {})

  // 여기서 매장 정보 받기
  // const getStoreInfor = function () {
  //   // window를 안쓰면 카카오를 읽을 수 없고
  //   // window를 쓰면 maps를 읽을 수 없구나...
  //   const geocoder = new window.kakao.maps.services.Geocoder() // 지금 여기서 5시간 째 문제 발생함
  //   console.log(geocoder)

  //   let coord = new window.kakao.maps.LatLng(data.Lat, data.Lng);
  //   let callback = function(result, status) {
  //       if (status === window.kakao.maps.services.Status.OK) {
  //           console.log(result);
  //       }
  //   };
  //   const userPointer = geocoder.coord2Address(coord.getLng(), coord.getLat(), callback); // 이렇게 하면 좌표값을 주소로 알려줍니다.

  //   placesSearchCB = function(data, status, pagination) {
  //       if (status === kakao.maps.services.Status.OK) {

  //           // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
  //           // LatLngBounds 객체에 좌표를 추가합니다
  //           var bounds = new kakao.maps.LatLngBounds();

  //           for (var i=0; i<data.length; i++) {
  //               displayMarker(data[i]);    
  //               bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
  //           }       

  //           // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
  //           map.setBounds(bounds);
  //       } 
  //   }


  //   const ps = new kakao.maps.services.Places(); 
  //   ps.keywordSearch('이태원 맛집', placesSearchCB); 



  // }
  // getStoreInfor()

  // tokenSave, data, getStoreInfor
  return {  }
}
}
</script>
<style>
  
</style>