<template>
  <div class="my-page-header" style="width: 100vw; height:100%">
    <profile-image :user="state.user"/>
  </div>

  <profile-desc :user="state.user" style="margin-bottom:40px;"/>
  <hr>
  <profile-edit-dialog :user="state.user" style="margin: 15px 20px 0px 0px;"/>
  <div class="my-divider"></div>

  <profile-goal :user="state.user"/>
  <profile-badge :user="state.user"/>
  <profile-calendar :user="state.user"/>
</template>

<style scoped>
  .my-page-header {
    margin: -20px -20px -20px -20px;
  }
  .my-divider {
    height:5px;
    background:#c6c6c6a8;
    margin: 100px -20px 10px -20px;
  }
</style>

<script>
import { reactive, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter, useRoute } from 'vue-router'
import axios from 'axios';
import ProfileBadge from './conponents/profile-badge.vue'
import ProfileCalendar from './conponents/profile-calendar.vue'
import ProfileDesc from './conponents/profile-desc.vue'
import ProfileGoal from './conponents/profile-goal.vue'
import ProfileImage from './conponents/profile-image.vue'
import ProfileEditDialog from './conponents/profile-edit-dialog.vue'


export default {
  name: 'mypage',

	components: {
		ProfileBadge,
    ProfileDesc,
    ProfileCalendar,
    ProfileGoal,
    ProfileImage,
    ProfileEditDialog,
	},

  setup() {
    const store = useStore()
    const router = useRouter()
    const route = useRoute()
    const state = reactive({
      user : {
        departmentId : undefined,
        id : undefined,
        goal : undefined,
        goalTime : undefined,
        nickName : undefined,
        rivalCount : undefined,
        thumbnail : undefined,
        userId : undefined,
      }
    })

    // life cycle
    onMounted(() => {
      getUser()
      console.log(state.user)
    })

    const getUser = function () {
      axios.get(`users/${route.params.userId}`)
        .then(res => {
          for (let i in Object.keys(state.user)) {
            let userDataKey = Object.keys(state.user)[i]
            state.user[userDataKey] = res.data[userDataKey]
          }
        })
        .catch(err => {
          console.log(err)
        })
    }




    return {state, store, router, route}
  }

}

</script>
