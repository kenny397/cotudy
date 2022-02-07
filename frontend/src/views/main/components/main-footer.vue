<template>
  <div class="main-footer">
    <div class="contents">
      <el-link style="margin-end:10px" href="https://www.ssafy.com/ksp/jsp/swp/swpMain.jsp">공지사항</el-link>
      <el-link href="https://www.ssafy.com/ksp/jsp/swp/swpMain.jsp">서비스 이용약관 변경 안내</el-link>
      <p>대전광역시 유성구 동서대로 98-39 / 042-820-7385 / ssafy@ssafy.com</p>
      <p>Copyright © SAMSUNG All Rights Reserved.</p>
    </div>
  </div>
</template>

<script>
// import { reactive } from 'vue'
// import { useStore } from 'vuex'

export default {
  name: 'main-footer',

//   props: {
//     height: {
//       type: String,
//       default: '110px'
//     }
//   },

//   setup() {
//     const state = reactive({})

//     return { state }
//   }
}
</script>

<style scoped>
.main-footer .contents {
  text-align: center;
  margin-bottom: 30px;
}

/* @media (max-width: 700px) {
  .main-footer {
    height: 110px;
  }
  .main-footer .contents {
    line-height: 50px;
    text-align: center;
    margin: 0 auto;
  }
} */

/* @media (min-width: 701px) {
  .main-footer {
    height: 110px;
  }
  .main-footer .contents {
    line-height: 110px;
    text-align: center;
    margin: 0 auto;
  }
} */
</style>
