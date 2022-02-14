<template>
  <div>
    <el-dialog custom-class="login-dialog" title="Cotudy" v-model="state.emailDialogVisible" @close="handleClose">
      <h2>수험생들의 최선의,</h2>
      <h2 style="margin-bottom:20px;">그리고 최고의 선택!</h2>
      <p>지금 Cotudy에서 공부를 시작하세요.</p>
      <p style="margin-bottom:40px;">모든 수험생 여러분의 합격을 기원합니다.</p>

      <el-form :model="state.form" :rules="state.rules" ref="loginForm" :label-position="state.form.align">
        <el-form-item prop="id" label="e-mail" :label-width="state.formLabelWidth" >
          <el-input v-model="state.form.id" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="password" :label-width="state.formLabelWidth">
          <el-input v-model="state.form.password" autocomplete="off" show-password></el-input>
        </el-form-item>
      </el-form>

      <el-button round plain type="success" @click="clickLogin">
        <font-awesome-icon icon="clipboard-check"/>
        <span style="margin-left:10px">로그인하기</span>
      </el-button>

      <span style="margin-top:1.7rem; font-size: 0.7rem; color:grey;">cotudy는 수험생님의 개인정보를 안전하게 보호합니다.</span>
    </el-dialog>
  </div>
</template>
<style>
/* .login-dialog {
  width: 400px !important;
  height: 300px;
}
.login-dialog .el-dialog__headerbtn {
  float: right;
}
.login-dialog .el-form-item__content {
  margin-left: 0 !important;
  float: right;
  width: 200px;
  display: inline-block;
}
.login-dialog .el-form-item {
  margin-bottom: 20px;
}
.login-dialog .el-form-item__error {
  font-size: 12px;
  color: red;
}
.login-dialog .el-input__suffix {
  display: none;
}
.login-dialog .el-dialog__footer {
  margin: 0 calc(50% - 80px);
  padding-top: 0;
  display: inline-block;
}
.login-dialog .dialog-footer .el-button {
  width: 120px;
} */
.login-dialog {
  width: 350px !important;
  height: 500px;
}
.login-dialog .el-dialog__header {
  background-color: rgba(58, 194, 88, 1);
  text-align: center;
}
.login-dialog .el-dialog__header .el-dialog__title {
  font-size: 1.5rem;
  color: white;
  font-weight: bold;
}
.login-dialog .el-dialog__body {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.login-dialog h2 {
  margin-block-start: 10px;
  margin-block-end: 0;
}
.login-dialog p {
  margin-block-start: 5px;
  margin-block-end: 5px;
}
.login-dialog .el-form {
  margin-bottom: 15px;
}
.login-dialog .el-button--success.is-plain {
  --el-button-text-color: rgb(255, 255, 255);
  --el-button-bg-color: rgba(58, 194, 88, 1);
  --el-button-border-color: rgba(58, 194, 88, 1);
  --el-button-hover-text-color: rgba(58, 194, 88, 1);
  --el-button-hover-bg-color: rgb(255, 255, 255);
  --el-button-hover-border-color: rgba(58, 194, 88, 1);
  --el-button-active-text-color: rgb(255, 255, 255);
  --el-button-active-border-color: rgba(58, 194, 88, 1);
}
.login-dialog .el-button {
  width: 90%;
  height: 50px;
  border-radius: 30px !important;
  font-size: 1.2rem;
}
</style>
<script>
import { reactive, computed, ref, onMounted } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'login-dialog',

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const store = useStore()
    // 마운드 이후 바인딩 될 예정 - 컨텍스트에 노출시켜야함. <return>
    const loginForm = ref(null)

    /*
      // Element UI Validator
      // rules의 객체 키 값과 form의 객체 키 값이 같아야 매칭되어 적용됨
      //
    */
    const state = reactive({
      form: {
        id: '',
        password: '',
        align: 'left'
      },
      rules: {
        id: [
          {
            required: true,
            message: '이메일을 입력해주세요',
            trigger: 'blur'
          }
        ],
        password: [
          {
            required: true,
            message: '비밀번호를 입력해주세요',
            trigger: 'blur'
          }
        ]
      },
      emailDialogVisible: computed(() => props.open),
      formLabelWidth: '120px'
    })

    onMounted(() => {
      // console.log(loginForm.value)
    })

    const clickLogin = function () {
      // 로그인 클릭 시 validate 체크 후 그 결과 값에 따라, 로그인 API 호출 또는 경고창 표시
      loginForm.value.validate((valid) => {
        if (valid) {
          store.dispatch('root/requestLogin', { id: state.form.id, password: state.form.password })
          .then(function (result) {
            store.state['root'].isLogin = true
            emit('closeLoginDialog')
            localStorage.setItem('accessToken', result.data.accessToken)
            localStorage.setItem('userId', result.data.id)
          })
          .catch(function (err) {
            alert(err)
          })
        } else {
          alert('로그인에 실패하였습니다. 이메일 혹은 비밀번호를 확인해 주세요.')
        }
      });
    }

    const handleClose = function () {
      state.form.id = ''
      state.form.password = ''
      state.form.passwordDialigVisible = false
      emit('closeLoginDialog')
    }

    return { loginForm, state, clickLogin, handleClose }
  }
}
</script>
