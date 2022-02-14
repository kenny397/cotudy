<template>
  <div class="main-header">
    <div class="logo-wrapper" @click="goHome" />
    <span @click="goHome" style="cursor:pointer">
      Home
      <div v-if="state.nowRoute === 'home'" class="routeUnderline"></div>
    </span>
    <span @click="goRank" style="cursor:pointer">
      Rank
      <div v-if="state.nowRoute === 'rank'" class="routeUnderline"></div>
    </span>
    <!-- <p @click="goCommunity" style="cursor:pointer">Community</p> -->
    <el-autocomplete
      v-model="state.search"
      :fetch-suggestions="querySearch()"
      maxlength="20"
      placeholder="Studyroom Search"
      show-word-limit
      prefix-icon="el-icon-search"
      style="width:500px; --el-input-bg-color:rgba(243, 243, 245, 1);"
    />
    <el-button v-if="!state.isLoginButton" round plain type="success" @click="clickLogin">Signup/Login</el-button>
    <div v-if="state.isLoginButton">
      <el-button round plain type="success" @click="clickLogout">Logout</el-button>
      <el-button circle plain type="success" @click="clickLMypage">My</el-button>
    </div>
  </div>
</template>

<style scoped>
.main-header {
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 10px 20px;
}
.logo-wrapper {
  width: 70px;
  height: 50px;
  background-size: contain;
  background-repeat: no-repeat;
  background-image: url('../../../assets/images/main-logo.png');
  cursor: pointer;
}
.fixed {
  position: fixed;
  background-color: white;
}
.el-button--success.is-plain {
  --el-button-text-color: rgb(255, 255, 255);
  --el-button-bg-color: rgba(58, 194, 88, 1);
  --el-button-border-color: rgba(58, 194, 88, 1);
  --el-button-hover-text-color: rgba(58, 194, 88, 1);
  --el-button-hover-bg-color: rgb(255, 255, 255);
  --el-button-hover-border-color: rgba(58, 194, 88, 1);
  --el-button-active-text-color: rgb(255, 255, 255);
  --el-button-active-border-color: rgba(58, 194, 88, 1);
}
.routeUnderline {
  border-block-end: 5px solid rgba(58, 194, 88, 1);
}
</style>

<script>
import { reactive, computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import axios from 'axios'

export default {
  name: 'main-header',

  props: {
    height: {
      type: String,
      default: '70px'
    }
  },

  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()

    const state = reactive({
      searchValue: [],
      search: '',
      isLoginButton: computed(() => {
        return store.state['root'].isLogin
      }),
      nowRoute: 'home'
    })

    onMounted(() => {
      searchData()
    })

    const clickLogin = () => {
      emit('openLoginDialog')
    }

    const clickLogout = () => {
      localStorage.removeItem('accessToken')
      localStorage.removeItem('userId')
      router.go()
    }

    const clickLMypage = () => {
      state.nowRoute = 'myPage'
      router.push({
        name: 'my-page',
        params: {
          userId: localStorage.getItem('userId')*1
        }
      })
    }

    const goHome = function () {
      state.nowRoute = 'home'
      router.push({
        name: 'home',
      })
    }

    const goRank = function () {
      // if (!store.state['root'].isLogin) {
      //   alert('로그인 후 이용하실 수 있습니다!')
      //   clickLogin()
      // } else {
        state.nowRoute = 'rank'
        router.push({
          name: 'ranking',
        })
      // }

    }

    const searchData = function () {
      axios.get('rooms')
        .then(res => {
          for (let i in res.data) {
            state.searchValue.push(res.data[i].roomTitle)
          }
        })
        .catch(err => {
          console.log(err)
        })
    }

    const querySearch = function () {
      state.searchValue.filter
    }

    return {
      state,
      clickLogin,
      clickLogout,
      goHome,
      goRank,
      searchData,
      querySearch,
      clickLMypage
    }
  }
}
</script>

<style>
  /* .main-header {
    padding: 10px 20px;
  } */
  /*Mobile, Tablet*/
  /* .menu-icon-wrapper {
    display: inline-block;
    vertical-align: top;
    position: relative;
    top: 14px;
  }

  .main-header .hide-on-big .logo-wrapper {
    display: inline-block;
    margin: 0 calc(50% - 51px)
  }
  .main-header .hide-on-big .logo-wrapper .ic.ic-logo {
    width: 70px;
    height: 50px;
    background-size: contain;
    background-repeat: no-repeat;
    background-image: url('../../../assets/images/ssafy-logo.png');
  }
  .mobile-sidebar-wrapper {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
  }
  .mobile-sidebar-wrapper .mobile-sidebar {
    width: 240px; height: calc(100vh - 1px);
    display: inline-block;
    background-color: white;
    padding: 0 10px;
    vertical-align: top;
  }
  .mobile-sidebar-wrapper .mobile-sidebar .mobile-sidebar-tool-wrapper {
    padding-bottom: 20px;
  }
  .mobile-sidebar-wrapper .mobile-sidebar .mobile-sidebar-btn {
    display: block;
    margin: 0 auto;
    margin-top: 25px;
    height: 30px;
    width: 100%;
  }
  .mobile-sidebar-wrapper .mobile-sidebar .mobile-sidebar-btn.login-btn {
    color: white;
  }
  .mobile-sidebar-wrapper .mobile-sidebar .logo-wrapper {
    display: block
  }
  .mobile-sidebar-wrapper .mobile-sidebar .logo-wrapper .ic.ic-logo {
    width: 70px;
    height: 50px;
    margin: 0 auto;
    margin-top: 30px;
    background-size: contain;
    background-repeat: no-repeat;
    background-image: url('../../../assets/images/ssafy-logo.png');
  }
  .mobile-sidebar-wrapper .mobile-sidebar-backdrop {
    width: calc(100% - 260px); height: calc(100vh - 1px);
    background-color: black;
    display: inline-block;
    opacity: 0.3;
  }
  .mobile-sidebar-wrapper .el-menu{
    margin-top: 0;
    padding-left: 0;
    height: calc(100% - 235px);
  }
  .mobile-sidebar-wrapper .el-menu .el-menu-item {
    cursor: pointer;
  }
  .mobile-sidebar-wrapper .el-menu .el-menu-item .ic {
    margin-right: 5px;
  } */

  /*Desktop - Need to add Class if Need*/
  /* .main-header .hide-on-small .logo-wrapper {
    cursor: pointer;
    display: inline-block;
  }
  .main-header .hide-on-small .logo-wrapper .ic.ic-logo {
    width: 70px;
    height: 50px;
    background-size: contain;
    background-repeat: no-repeat;
    background-image: url('../../../assets/images/ssafy-logo.png');
  }
  .main-header .hide-on-small .tool-wrapper {
    width: 50%;
    float: right;
  }
  .main-header .hide-on-small .tool-wrapper .button-wrapper {
    width: 45%;
    float: right;
  }
  .main-header .hide-on-small .tool-wrapper .button-wrapper .el-button {
    width: 45%;
    height: 50px;
    cursor: pointer;
    margin-right: 1%;
  }
  .main-header .hide-on-small .tool-wrapper .search-field {
    width: 50%;
    height: 50px;
    max-width: 400px;
    margin-right: 2%;
    display: inline-block;
    background-color: white;
  }
  .main-header .hide-on-small .tool-wrapper .search-field .el-input {
    width: 100%;
    height: 100%;
  }
  .main-header .hide-on-small .tool-wrapper .search-field .el-input .el-input__inner {
    width: 88%;
    height: 50px;
    margin-right: 1%;
  }
  .main-header .hide-on-small .tool-wrapper .search-field .el-input .el-input__prefix {
    top: 5px;
  } */

</style>
