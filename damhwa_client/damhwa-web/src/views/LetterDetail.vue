<template>
  <div class="detail-bg">
    <h1 class="title">보낸 서신</h1>
    <img class="app-bar" src="~@/assets/back.png" alt="" @click="goToCalendar">
    <div>
      <img class="image-style" :src="data.flower.img1" alt="">
      <div>
        <p><span>{{ data.flower.fname_KR }}</span> : <span>{{ data.flower.flang }}</span></p>
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
import { onBeforeMount, reactive} from 'vue'
import { useRouter } from 'vue-router'

export default {
  name: 'LetterDetail',
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
    return { data, props, goToCalendar }
  }

}
</script>
<style>
.app-bar {
  position: relative;
}
.image-style {
  width: 200px;
  height: 200px;
}
</style>