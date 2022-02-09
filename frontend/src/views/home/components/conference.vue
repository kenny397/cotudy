<template>
  <el-card :body-style="{ padding: '0px' }">
    <div class="image-wrapper">
      <el-skeleton style="width: 100%">
        <template #template>
          <el-skeleton-item variant="image" style="width: 100%; height: 140px" />
        </template>
      </el-skeleton>
      </div>
    <div style="text-align: left; padding: 14px;">
      <span class="title">{{ title }}</span>
      <div class="bottom">
        <span>{{ desc }}</span>
      </div>
    </div>
  </el-card>
</template>
<style>
.el-card {
  margin: 0 8px;
  margin-bottom: 30px;
}
.el-card .image-wrapper {
  width: 100%;
  height: 100px;
}
.el-card .title {
  font-weight: bold;
}
.el-card .bottom {
  margin-top: 5px;
  display:-webkit-box;
  word-wrap:break-word;
  -webkit-box-orient:vertical;
  overflow:hidden;
  text-overflow:ellipsis;
}
/* 테블릿, 모바일의 경우 두 줄 말줄임표시 */
/* @media (max-width: 1269px) {
  .el-card .bottom {
    -webkit-line-clamp: 2;
    height:42px;
  }
} */
/* 데스크탑의 경우 세 줄 말줄임표시 */
@media (min-width: 1270px) {
  .el-card .bottom {
    -webkit-line-clamp: 3;
    height:60px;
  }
}

</style>
<script>
// import { reactive, onBeforeMount } from 'vue'
// import { useStore } from 'vuex'
// import axios from 'axios'

export default {
  name: 'Home',

  props: {
    title: {
      type: String,
      default: 'Studyroom'
    },
    desc: {
      type: String,
      default: '#공부'
    }
  },



  setup () {

  }
}
</script>
