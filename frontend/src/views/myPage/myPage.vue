<template>
  <div class="my-page-header" style="width: 100vw; height:100%">
    <profile-image :user="state.user"/>
  </div>
  <profile-desc :user="state.user" style="margin-bottom:40px;"/>
  <hr>
  <profile-edit-dialog :user="state.user" style="margin: 15px 20px 0px 0px;"/>
  <div class="my-divider"></div>
  <div class="my-page-body">
    <div class="resolution-badge-wrapper">
      <div>
        <div class="info-container">나의 목표</div>
        <profile-goal :user="state.user"/>
      </div>
      <div>
        <div class="info-container badge-header">나의 뱃지</div>
        <profile-badge :user="state.user"/>
      </div>
    </div>
    <profile-calendar :user="state.user"/>
  </div>
</template>

<style scoped>
  .info-container {
    font-size: 20px;
    margin-left: 10px;
    margin-bottom: 5px;
  }
  .my-page-body {
    margin: 0px 50px;
  }
  .my-page-header {
    margin: -20px -20px -20px -20px;
  }
  .my-divider {
    height:5px;
    background:#c6c6c6a8;
    margin: 100px -20px 10px -20px;
  }
  .resolution-badge-wrapper {
    background: #E0E0E0;
    display: flex;
    justify-content :space-around;
    flex-direction: row;
    padding:15px 0px 30px 0px;
    border-radius: 30px;
    text-align: left;
  }
  @media (max-width: 1150px) {
    .badge-header {
      display: none;
    }
    .my-badge-container {
      display: none;
    }
    .my-goal-container {
      width: 75vw;
    }
    .resolution-badge-wrapper {
      justify-content: center;
    }
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
