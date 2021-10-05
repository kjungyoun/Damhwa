<template>
  <div class="detail-bg">
    <div class="d-flex">
      <div style="flex: 1;">
        <img style="border-radius: 150px;" class="mt-4 mx-2 app-bar" src="~@/assets/back.png" @click="goToCalendar" alt="">
      </div>
      <div style="flex: 7;">
        <h1 class="title m-4">추천 꽃</h1>  
      </div>
      <div style="flex: 1;"></div>
    </div>
    
    <div class="mt-3 mx-3 d-flex justify-content-start" style="color: #7C947D; font-size: 20px;">
      {{ data.date }}
    </div>
    <div class="d-flex justify-content-center">
      <img style="border-radius: 150px;" class="image-style p-3" :src="data.history.flower.watercolor_img" alt="">
      <div class="align-self-center">
        <h4 align="left">{{data.history.flower.fname_KR}}</h4>
        <h5 align="left">{{data.history.flower.flang}}</h5>
      </div>
    </div>
    <div class="list">
      <div class="m-3 d-flex justify-content-start">
        [작성한 내용]
      </div>
      <div class="m-3" style="word-break:break-all; text-align: justify">
        {{ data.history.contents }}
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import constants from "../util/constants";
import { onBeforeMount, reactive } from 'vue'
import { useRouter } from 'vue-router'
export default {
  name: 'FeelingDetail',
  props: {
    historyId: {
      type: Number,
      default: 0
    }
  },
  setup(props) {
    const router = useRouter()
    let data = reactive({
      history: {},
      date: ""
    })
    onBeforeMount(() => {
      axios.get(constants.BASE_URL_GET_HISTORY, {
          params: {
            hno: props.historyId,
          },
      })
      .then(res => {
        data.history = res.data
        console.log(data.history)
        data.date = data.history.regdate
        data.date = data.date.slice(0, 10)
      })
      .catch(err => {
        console.log(err)
      })
    })
    const goToCalendar = () => {
      router.back()
    }
    return { data, props, goToCalendar }
  }

}
</script>

<style scoped>
.image-style {
  width: 200px;
  height: 200px;
}

.list {
  width: 95%;
  max-height: 100%;
  margin: auto;
  padding: 10px;
  background-color: #ffffff85;
  border-radius: 12px;
  font-family: "SangSangRock";
}

</style>