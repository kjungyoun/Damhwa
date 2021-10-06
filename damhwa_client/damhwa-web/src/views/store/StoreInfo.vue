<template>
  <div>
    <KakaoMap
      @stores="listing"
    />
    <div v-for="(store, idx) in this.stores" :key="idx">
      <p>{{ store.place_name }}</p>
      <p>{{ store.phone }}</p>
    </div>
  </div>
</template>
<script>
import KakaoMap from "./components/KakaoMap.vue"
export default {
  name: "StoreInfo",
  components: {
    KakaoMap,
  },
  data() {
    return {
      stores: []
    }
  },
  methods: {
    listing(stores) {
      this.stores = stores
    }
  }
}
</script>
<style>
  
</style>