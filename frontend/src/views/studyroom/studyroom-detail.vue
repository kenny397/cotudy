<template>
  <p style="margin-bottom:20px">{{ $route.params.studyroomId + '번 방 상세 보기 페이지' }}</p>
  <el-button @click="enterStandby($route.params.studyroomId)">입장하기</el-button>
</template>
<style>
</style>
<script>
import { reactive, onMounted, onUnmounted } from 'vue'
import { useStore } from 'vuex'
import { useRoute, useRouter } from 'vue-router'

export default {
  name: 'studyroom-detail',

  setup () {
    const route = useRoute()
    const router = useRouter()
    const store = useStore()
    const state = reactive({
      studyroomId: ''
    })

    // 페이지 진입시 불리는 훅
    onMounted(() => {
      state.studyroomId = route.params.studyroomId
      store.commit('root/setMenuActiveMenuName', 'home')
    })

    // 페이지 이탈시 불리는 훅
    onUnmounted(() => {
      state.studyroomId = ''
    })

    const enterStandby = function (id) {
      router.push({
        name: 'studyroom-standby',
        params: {
          studyroomId: id
        }
      })
    }

    return { state, enterStandby }
  },


}
</script>
