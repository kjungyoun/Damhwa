<template>
  <router-view/>
</template>

<style>
@font-face {
    font-family: 'SangSangRock';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/SangSangRockOTF.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

body {
  background: #F2EEE5;
  font-family: 'SangSangRock';
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: black;
}

.title {
  font-family: 'SangSangRock';
  color: black;
}

.detail-bg {
  height: 100vh;
  background-image: url("~@/assets/letter_bg.png");
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;
  font-family: 'SangSangRock';
}

.button {
  border: 3px solid black;
  background-color: white;
  padding: 8px 20px;
  border-radius: 20px;
  font-size: 15px;
  margin-top: 20px;
  color: black;
  font-family: 'SangSangRock';
}
</style>
