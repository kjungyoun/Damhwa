<template>
  <div class="detail-bg">
    <h1 class="title">추천 꽃</h1>
    <img class="app-bar" src="~@/assets/back.png" @click="goToCalendar" alt="">
    <p>{{ props.regdate }}</p>
    <div>
      <img class="image-style" :src="data.flower.img1" alt="">
      <div>
        <h3>{{ data.flower.fname_KR }}</h3>
        <p>{{ data.flower.fcontents }}</p>
      </div>
      <div>
        {{ props.contents }}
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
      flower: {}
    })
    onBeforeMount(() => {
      axios.get(constants.BASE_URL_GET_FLOWER, {
         params: {
            fno: props.fno,
          },
      })
      .then(res => {
        data.flower = res.data
      })
    })
    const goToCalendar = () => {
      router.back()
    }
    return { data, props, goToCalendar}
  }

}
</script>

<style>


</style>