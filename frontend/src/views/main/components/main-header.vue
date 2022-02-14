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
    <div>
      <input class="inputStyle" type="text" placeholder="Search Studyroom" @input="searchChange($event)">
      <el-card v-if="state.isSearch" class="searchBox">
        <div class="searchHeader">
          <span class="headerFont" @click="clickExit()" style="cursor:pointer;">
            [<font-awesome-icon icon="x"/>] 닫기
          </span>
        </div>
        <div v-for="i in state.showSearchData" :key="i.roomId">
          <div @click="clickConference(i)" style="cursor:pointer; margin-bottom:5px; display:flex; justify-content:space-between; align-items:center;">
            <div>
              <span style="font-size:1rem; font-weight:bolder; margin-end:5px;">#{{ i.roomId }}</span>
              <span>{{ i.title }}</span>
            </div>
            <span style="font-size:0.8rem; text-align:end;">({{ i.headCount }} / {{ i.maxPeople }})</span>
          </div>
          <hr>
        </div>
      </el-card>
      <!-- <div v-if="state.isSearch" class="searchBox">
        <div class="searchHeader">
          <span class="headerFont" @click="clickExit()">
            [<font-awesome-icon icon="x"/>] 닫기
          </span>
        </div>
      </div> -->
    </div>
    <el-button v-if="!state.isLoginButton" round plain type="success" @click="clickLogin">Signup/Login</el-button>
    <div v-if="state.isLoginButton">
      <el-button round plain type="success" @click="clickLogout">Logout</el-button>
      <el-button circle plain type="success" @click="clickLMypage">My</el-button>
    </div>
  </div>
</template>

<style scoped>
input::-webkit-input-placeholder { font-size: 90%; }
input::-moz-placeholder { font-size: 90%; }
input:-ms-input-placeholder { font-size: 90%; }
input:-moz-placeholder { font-size: 90%; }
input::placeholder { font-size: 90%; }

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
.searchBox {
  position: absolute;
  width: 522px;
  margin-top:5px;
  z-index: 2000;
}
.searchBox .searchHeader {
  padding-bottom: 20px;
  text-align: end;
}
.searchBox .searchHeader .headerFont{
  font-size:0.8rem;
  margin-right: 10px;
  cursor: pointer;
}
.inputStyle {
  background-color: rgba(243, 243, 245, 1);
  border: none;
  border-radius: 8px;
  display: inline-block;
  font-size: inherit;
  height: 40px;
  line-height: 10px;
  padding: 0 11px;
  width: 500px;
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
      isSearch: false,
      searchValue: [],
      showSearchData: [],
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
            const tmp = {
              roomId: res.data[i].roomId,
              roomTitle: res.data[i].roomTitle,
              headCount: res.data[i].headCount,
              roomMaxPeople: res.data[i].roomMaxPeople
            }
            state.searchValue.push(tmp)
          }
          console.log(state.searchValue)
        })
        .catch(err => {
          console.log(err)
        })
    }

    const clickConference = function (id) {
      if (!store.state['root'].isLogin) {
        alert('로그인 후 이용하실 수 있습니다!')
        // TODO 로그인 모달 켜기
      } else if (id.headCount >= id.roomMaxPeople) {
        alert('허용인원이 초과되어 스터디룸에 입장하실 수 없습니다! \n다른 스터디룸을 이용해 주세요.')
      } else {
        router.push({
          name: 'studyroom-standby',
          params: {
            studyroomId: id.roomId
          }
        })
      }
    }

    const searchChange = function (event) {
      state.isSearch = true
      state.showSearchData = []
      const v = event.target.value
      console.log(v)
      const l = v.length
      for (let i in state.searchValue) {
        const title = state.searchValue[i].roomTitle
        const headCount = state.searchValue[i].headCount
        const maxPeople = state.searchValue[i].roomMaxPeople
        const roomId = state.searchValue[i].roomId
        for (let j in title) {
          if (v === title.slice(j,(j*1)+(l*1))) {
            state.showSearchData.push({
              title: title,
              headCount: headCount,
              maxPeople: maxPeople,
              roomId: roomId
            })
            break
          }
        }
      }
    }

    const clickExit = function () {
      state.isSearch = false
    }

    return {
      state,
      clickLogin,
      clickLogout,
      goHome,
      goRank,
      searchData,
      clickLMypage,
      clickConference,
      searchChange,
      clickExit,
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
