<template>
  <el-container
    class="main-wrapper"
    v-if="!isFullpages.includes($route.name)"
  >
    <main-header @openLoginDialog="onOpenLoginDialog" />
    <el-container class="main-container">
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
    <main-footer />
  </el-container>
  <el-container
    class="main-wrapper"
    v-if="isFullpages.includes($route.name)"
  >
    <router-view></router-view>
  </el-container>
  <login-dialog
    :open="loginDialogOpen"
    @closeLoginDialog="onCloseLoginDialog"/>
  <signup-dialog />
</template>

<style>
  @import "//cdn.jsdelivr.net/npm/element-plus/dist/index.css";
  @import './main.css';
  @import '../../common/css/common.css';
  @import '../../common/css/element-plus.css';
</style>

<script>
import LoginDialog from './components/login-dialog'
import MainHeader from './components/main-header'
import MainFooter from './components/main-footer'
import SignupDialog from './components/signup-dialog'

export default {
  name: 'Main',
  components: {
    MainHeader,
    MainFooter,
    LoginDialog,
    SignupDialog
  },
  data () {
    return {
      loginDialogOpen: false,
      isFullpages: [
        'studyroom-standby',
        'studyroom-inside'
      ]
    }
  },
  methods: {
    onOpenLoginDialog () {
      this.loginDialogOpen = true
    },
    onCloseLoginDialog () {
      this.loginDialogOpen = false
    }
  },
  beforeMount() {
    if (localStorage.getItem('accessToken') === null) {
      this.$store.state.root.isLogin = false
    } else {
      this.$store.state.root.isLogin = true
    }
  }
}
</script>
