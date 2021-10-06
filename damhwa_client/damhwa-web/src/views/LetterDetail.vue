<template>
  <div class="container detail-bg">
    <div class="d-flex">
      <div style="flex: 1">
        <img
          class="app-bar mt-4 mx-2"
          src="~@/assets/back.png"
          alt=""
          @click="goToCalendar"
        />
      </div>
      <div style="flex: 7">
        <h1 class="title m-4">보낸 서신</h1>
      </div>
      <div style="flex: 1"></div>
    </div>
    <div
      class="mt-3 mx-3 d-flex justify-content-start"
      style="color: #7c947d; font-size: 20px"
    >
      {{ data.date }}
    </div>
    <div style="flex: 1">
      <img
        style="border-radius: 150px"
        class="image-style p-4 d"
        :src="data.history.flower.watercolor_img"
        alt=""
      />
    </div>
    <div style="flex: 1">
      <h3>
        {{ data.history.flower.fname_KR }}: {{ data.history.flower.flang }}
      </h3>
    </div>
    <div class="container">
      <div class="list">
        <div
          v-if="data.history.receiver"
          class="me-2 ms-2 mt-2 d-flex justify-content-start"
        >
          [받은 사람]: {{ data.history.receiver }}
        </div>
        <div class="m-2 d-flex justify-content-start">
          <div style="font-size: 20px">[편지 내용]</div>
        </div>
        <div class="m-3" style="word-break: break-all; text-align: justify">
          {{ data.history.contents }}
        </div>
      </div>
      <div class="list mt-2">
        <div class="m-3 d-flex justify-content-start" style="font-size: 20px">
          [이야기]
        </div>
        <p class="m-3" style="word-break: break-all; text-align: justify">
          {{ data.history.flower.fcontents }}
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import constants from "../util/constants";
import { reactive } from "vue";
import { useRouter } from "vue-router";

export default {
  name: "LetterDetail",
  props: {
    historyId: {
      type: String,
      default: "",
    },
  },
  setup(props) {
    const router = useRouter();
    let data = reactive({
      history: {
        flower: {},
      },
      date: "",
    });
    const getFlowerInfo = function () {
      axios.get(constants.BASE_URL_GET_HISTORY, {
          params: {
            hno: props.historyId,
          },
        })
        .then((res) => {
          data.history = res.data;
          let realDate = new Date(data.history.regdate);
          realDate.setHours(realDate.getHours() + 9);
          data.date = realDate.toISOString().slice(0, 10);
        })
        .catch((err) => {
          err;
        });
    };
    getFlowerInfo();
    const goToCalendar = () => {
      router.back();
    };
    return { data, props, goToCalendar };
  },
};
</script>
<style scoped>
.app-bar {
  position: relative;
}
.image-style {
  width: 200px;
  height: 200px;
}

.list {
  width: 95%;
  min-height: 100px;
  max-height: 100%;
  margin: auto;
  padding: 10px;
  background-color: #ffffff85;
  border-radius: 12px;
  font-family: "SangSangRock";
}
</style>