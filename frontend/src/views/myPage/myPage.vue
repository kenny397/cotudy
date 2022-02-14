<template>




  <el-calendar v-model="value" />
</template>

<script>
import { onBeforeMount } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'mypage',

  setup() {
    const store = useStore()
    const router = useRouter()

    onBeforeMount(() => {
      isLogin()
    })

    const isLogin = function () {
      if (!store.state['root'].isLogin) {
        router.push({
          name:'home'
        })
      }
    }
  }

}

// const value = ref(new Date())

</script>
