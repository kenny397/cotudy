<template>
  <p style="margin-bottom:20px">{{ $route.params.studyroomId + '번 방 상세 보기 페이지' }}</p>
  <p>방 이름 : {{ state.studyroomDetailValue.roomTitle }}</p>
  <p>현재 방 인원 수({{ state.studyroomDetailValue.headCount }}) / 방 최대 인원({{ state.studyroomDetailValue.roomMaxPeople }})</p>
  <p>방 카테고리 : {{ state.studyroomDetailValue.roomCategory }}</p>
  <p>방 설명 : {{ state.studyroomDetailValue.roomDescription }}</p>
  <p>방 썸네일 번호 : {{ state.studyroomDetailValue.roomThumbnail }}</p>
  <el-button @click="enterStandby($route.params.studyroomId)">입장하기</el-button>
</template>
<style>
</style>
<script>
import { reactive, onMounted, onUnmounted } from 'vue'
import { useStore } from 'vuex'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'

export default {
  name: 'studyroom-detail',

  setup () {
    const route = useRoute()
    const router = useRouter()
    const store = useStore()
    const state = reactive({
      studyroomId: '',
      studyroomDetailValue: {
        headCount: 0,
        roomCategory: '',
        roomDescription: '',
        roomMaxPeople: 0,
        roomThumbnail: '',
        roomTitle: '',
      }
    })

    // 페이지 진입시 불리는 훅
    onMounted(() => {
      state.studyroomId = route.params.studyroomId
      store.commit('root/setMenuActiveMenuName', 'home')
      studyroomDetail(state.studyroomId)
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

    const studyroomDetail = function (id) {
      axios.get(`rooms/${id}`)
        .then(res => {
          return res.data
        })
        .then(res => {
          for (let vKey in state.studyroomDetailValue) {
            for (let resKey in res) {
              if (resKey === vKey) {
                state.studyroomDetailValue[vKey] = res[resKey]
                break
              }
            }
          }
        })
        .catch(err => {
          console.log(err)
        })
    }

    return { state, enterStandby, studyroomDetail }
  },


}
</script>
