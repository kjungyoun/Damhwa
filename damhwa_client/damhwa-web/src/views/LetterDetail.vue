<template>
  <div class="detail-bg">
    <h1 class="title">보낸 서신</h1>
    <img class="app-bar" src="~@/assets/back.png" alt="">
    <div>
      <img class="image-style" src="https://cdn.pixabay.com/photo/2015/04/19/08/32/marguerite-729510__480.jpg" alt="">
      <div>
        <p><span>엉겅퀴</span> : <span>독립</span></p>
      </div>
      <div>
        블라블라
      </div>
      <button class="button"> 다시 전하기 </button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LetterDetail'
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