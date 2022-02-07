<template>
  <el-container class="main-wrapper">
    <main-header
      @openLoginDialog="onOpenLoginDialog"
      v-if="$route.name != 'studyroom-standby'"
    />
    <hr v-if="$route.name != 'studyroom-standby'">
    <el-container class="main-container">
      <el-main v-if="$route.name != 'studyroom-standby'">
        <router-view></router-view>
      </el-main>
      <router-view v-if="$route.name ==='studyroom-standby'"></router-view>
    </el-container>
    <hr v-if="$route.name!='studyroom-standby'">
    <main-footer v-if="$route.name != 'studyroom-standby'"/>
  </el-container>
  <login-dialog
    :open="loginDialogOpen"
    @closeLoginDialog="onCloseLoginDialog"/>
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

export default {
  name: 'Main',
  components: {
    MainHeader,
    MainFooter,
    LoginDialog
  },
  data () {
    return {
      loginDialogOpen: false
    }
  },
  methods: {
    onOpenLoginDialog () {
      this.loginDialogOpen = true
    },
    onCloseLoginDialog () {
      this.loginDialogOpen = false
    }
  }
}
</script>
