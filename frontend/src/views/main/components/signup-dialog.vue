<template>
  <div>
    <el-dialog custom-class="signup-dialog" title="Cotudy" v-model="state.signupDialogVisible" @close="handleClose">
      <el-form :model="state.form" :rules="state.rules" ref="loginForm" :label-position="state.form.align">
        <div style="display:flex;">
          <el-form-item prop="email" label="e-mail" :label-width="state.formLabelWidth">
            <el-input type="email" v-model="state.form.email" autocomplete="off" @keyup.enter="clickSignup" :disabled="state.emailRepe"></el-input>
          </el-form-item>
          <el-button @click="emailCheck()" style="margin-start:10px;">중복확인</el-button>
        </div>
        <el-form-item prop="password" label="비밀번호" :label-width="state.formLabelWidth">
          <el-input v-model="state.form.password" autocomplete="off" show-password @keyup.enter="clickSignup"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword" label="비밀번호확인" :label-width="state.formLabelWidth">
          <el-input v-model="state.form.confirmPassword" autocomplete="off" show-password @keyup.enter="clickSignup"></el-input>
        </el-form-item>
        <div style="display:flex;">
          <el-form-item prop="nickname" label="nickname" :label-width="state.formLabelWidth">
            <el-input v-model="state.form.nickname" autocomplete="off" @keyup.enter="clickSignup" :disabled="state.nickRepe"></el-input>
          </el-form-item>
          <el-button @click="nickCheck()" style="margin-start:10px;">중복확인</el-button>
        </div>
      </el-form>
      <el-button round plain type="success" @click="clickSignup" style="margin-top:-10px;">
        <font-awesome-icon icon="user-plus"/>
        <span style="margin-left:10px">회원가입 하기</span>
      </el-button>
      <span style="margin-top:1rem; font-size: 0.7rem; color:grey;">cotudy는 수험생님의 개인정보를 안전하게 보호합니다.</span>
    </el-dialog>
  </div>
</template>

<style>
.signup-dialog {
  width: 450px !important;
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
        confirmPassword: '',
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
            trigger: ['blur', 'change'],
          }
        ],
        confirmPassword: [
          {
            required: true,
            message: '비밀번호를 확인해 주세요',
            trigger: ['blur', 'change'],
          },
        ],
        nickname: [
          {
            required: true,
            message: '닉네임을 입력해주세요',
            trigger: ['blur', 'change'],
          }
        ]
      },
      signupDialogVisible: computed(() => store.getters['root/getIsSignupOpen']),
      formLabelWidth: '100px',
      emailRepe: false,
      nickRepe: false,
    })

    const clickSignup = function () {
      if (state.form.email === '') {
        alert('이메일을 입력해 주세요')
        return
      }
      if (state.form.password === '') {
        alert('비밀번호를 작성해 주세요')
        return
      }
      if (state.form.confirmPassword === '') {
        alert('비밀번호 확인을 작성해 주세요')
        return
      }
      if (state.form.nickname === '') {
        alert('닉네임을 작성해 주세요')
        return
      }
      if (!state.emailRepe) {
        alert('이메일 중복확인을 진행해 주세요!')
        return
      }
      if (!state.nickRepe) {
        alert('닉네임 중복확인을 진행해 주세요!')
        return
      }
      if (state.form.password != state.form.confirmPassword) {
        alert('비밀번호를 일치시켜 주세요')
        return
      }
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

    const emailCheck = function () {
      axios.get(`users/check/id/${state.form.email}`)
        .then(res => {
          console.log(res.data)
          return res.data
        })
        .then(res => {
          if (res.count === 0) {
            console.log(res.count)
            state.emailRepe = true
            alert('사용가능한 이메일입니다.')
          } else {
            alert('사용중인 이메일입니다. 다른 이메일을 입력하세요')
          }
        })
        .catch(err => {
          alert(err)
        })
    }

    const nickCheck = function () {
      axios.get(`users/check/nickName/${state.form.nickname}`)
        .then(res => {
          return res.data
        })
        .then(res => {
          if (res.count === 0) {
            state.nickRepe = true
            alert('사용가능한 닉네임입니다.')
          } else {
            alert('사용중인 닉네임입니다. 다른 닉네임을 입력하세요')
          }
        })
        .catch(err => {
          alert(err)
        })
    }

    const handleClose = function () {
      state.form.email = ''
      state.form.password = ''
      state.form.nickname = ''
      state.emailRepe = false
      state.nickRepe = false
      store.commit('root/signupClose')
    }

    return { signupForm, state, clickSignup, handleClose, emailCheck, nickCheck }
  }
}
</script>
