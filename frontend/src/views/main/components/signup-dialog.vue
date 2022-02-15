<template>
  <div>
    <div v-if="state.signupDialogVisible" style="backgrond-color:black; width:300px; height:300px">

    </div>
    <el-dialog custom-class="signup-dialog" title="Cotudy" v-model="state.signupDialogVisible" @close="handleClose">
      <el-form :model="state.form" :rules="state.rules" ref="loginForm" :label-position="state.form.align">
        <el-form-item prop="email" label="e-mail" :label-width="state.formLabelWidth" >
          <el-input v-model="state.form.email" autocomplete="off" @keyup.enter="clickSignup"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="password" :label-width="state.formLabelWidth">
          <el-input v-model="state.form.password" autocomplete="off" show-password @keyup.enter="clickSignup"></el-input>
        </el-form-item>
        <el-form-item prop="nickname" label="nickname" :label-width="state.formLabelWidth">
          <el-input v-model="state.form.nickname" autocomplete="off" @keyup.enter="clickSignup"></el-input>
        </el-form-item>
      </el-form>
      <el-button round plain type="success" @click="clickSignup">
        <font-awesome-icon icon="user-plus"/>
        <span style="margin-left:10px">회원가입 하기</span>
      </el-button>
      <span style="margin-top:1rem; font-size: 0.7rem; color:grey;">cotudy는 수험생님의 개인정보를 안전하게 보호합니다.</span>
    </el-dialog>
  </div>
</template>
<style>
.signup-dialog {
  width: 350px !important;
  height: 330px;
}
.signup-dialog .el-dialog__header {
  background-color: rgba(58, 194, 88, 1);
  text-align: center;
}
.signup-dialog .el-dialog__header .el-dialog__title {
  font-size: 1.5rem;
  color: white;
  font-weight: bold;
}
.signup-dialog .el-dialog__body {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.signup-dialog h2 {
  margin-block-start: 10px;
  margin-block-end: 0;
}
.signup-dialog p {
  margin-block-start: 5px;
  margin-block-end: 5px;
}
.signup-dialog .el-form {
  margin-bottom: 10px;
}
.signup-dialog .el-button--success.is-plain {
  --el-button-text-color: rgb(255, 255, 255);
  --el-button-bg-color: rgba(58, 194, 88, 1);
  --el-button-border-color: rgba(58, 194, 88, 1);
  --el-button-hover-text-color: rgba(58, 194, 88, 1);
  --el-button-hover-bg-color: rgb(255, 255, 255);
  --el-button-hover-border-color: rgba(58, 194, 88, 1);
  --el-button-active-text-color: rgb(255, 255, 255);
  --el-button-active-border-color: rgba(58, 194, 88, 1);
}
.ignup-dialog .el-button {
  width: 90%;
  height: 50px;
  border-radius: 30px !important;
  font-size: 1.2rem;
}

</style>
<script>
import { reactive, ref, computed } from 'vue'
import { useStore } from 'vuex'
import axios from 'axios'

export default {
  name: 'signup-dialog',

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup() {
    const store = useStore()
    const signupForm = ref(null)

    const state = reactive({
      form: {
        email: '',
        password: '',
        nickname: '',
        align: 'left'
      },
      rules: {
        email: [
          {
            required: true,
            message: '이메일을 입력해주세요',
            trigger: 'blur'
          },
          {
            type: 'email',
            message: '올바른 이메일 형식으로 작성해 주세요',
            trigger: ['blur', 'change'],
          },
        ],
        password: [
          {
            required: true,
            message: '비밀번호를 입력해주세요',
            trigger: 'blur'
          }
        ],
        nickname: [
          {
            required: true,
            message: '닉네임을 입력해주세요',
            trigger: 'blur'
          }
        ]
      },
      signupDialogVisible: computed(() => store.getters['root/getIsSignupOpen']),
      formLabelWidth: '100px',
    })

    const clickSignup = function () {
      console.log(signupForm)
      // 로그인 클릭 시 validate 체크 후 그 결과 값에 따라, 로그인 API 호출 또는 경고창 표시
      axios({
        url: 'users',
        method: 'post',
        data: {
          'email': state.form.email,
          'nickName': state.form.nickname,
          'password': state.form.password
        }
      })
        .then(res => {
          console.log(res)
          alert('회원가입이 완료되었습니다! 로그인 해주세요')
          handleClose()
        })
        .catch(err => {
          console.log(err)
        })
    }

    const handleClose = function () {
      state.form.email = ''
      state.form.password = ''
      store.commit('root/signupClose')
    }

    return { signupForm, state, clickSignup, handleClose }
  }
}
</script>
