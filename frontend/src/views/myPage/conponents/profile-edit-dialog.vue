<template>
  <el-button
      v-if="state.user.isMe"
      round
      @click="profileDialogOpen()"
      class="editDialogBtn"
    >
      <font-awesome-icon icon="pen-to-square" style="float:right; font-size:20px;"/>
      <span style="margin-left : 5px;">회원정보 수정</span>
  </el-button>
  <el-dialog
    v-model="state.profileEditDialogVisible"
    title="회원정보 수정"
    width="30%"
    custom-class="editDialog"
  >
    <el-form
      ref="ruleFormRef"
      :model="state.profileForm"
      :rules="state.rules"
      label-width="120px"
    >
      <el-form-item label="닉네임" prop="nickName">
        <el-input
          v-model="state.profileForm.nickName"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item label="소속" prop="myDepartment">
        <el-cascader
          v-model="state.profileForm.myDepartment"
          :options="state.departmentOption"
        ></el-cascader>
      </el-form-item>
      <el-form-item label="목표 공부시간" prop="goalTime">
        <el-cascader
          v-model="state.profileForm.goalTime"
          :options="state.goalTimeOptions"
        ></el-cascader>
      </el-form-item>
      <el-form-item label="나의 다짐" prop="goal">
        <el-input
          v-model="state.profileForm.goal"
          autocomplete="off"
        ></el-input>
      </el-form-item>

      <el-button @click="editSubmit()">수정</el-button>
      <el-button @click="handleClose()">취소</el-button>

    </el-form>
  </el-dialog>
</template>

<style>
  .editDialogBtn {
    background: #3ac257;
    color: white;
    float:right;
    height:50px;
    font-size:20px;
    margin-top: 15px;
  }
  .editDialogBtn:hover{
    background: white;
    border: 1px solid #3ac257;
    color: #3ac257
  }
  .editDialogBtn:focus{
    background: white;
    border: 1px solid #3ac257;
    color: #3ac257
  }
  .editDialog {
    background: white;
    border-radius: 20px;
  }
  .editDialog input{
    background: white;
    border-radius: 20px;
  }



</style>

<script>
import {reactive} from 'vue';
import axios from 'axios';

export default {
  name: 'profile-edit-dialog',

  props: {
		user: Object,
	},
  setup (props) {
    const state = reactive({
      user: props.user,
      profileEditDialogVisible : false,
      department : {
        1: 'SSAFY',
        2: '삼성전자',
        3: '삼성멀티캠퍼스',
        4: '대전고등학교',
        5: '서울고등학교',
        10: '소속없음',
      },
      departmentOption : [
        {value: 'SSAFY', label: 'SSAFY'},
        {value: '삼성전자', label: '삼성전자'},
        {value: '삼성멀티캠퍼스', label: '삼성멀티캠퍼스'},
        {value: '대전고등학교', label: '대전고등학교'},
        {value: '서울고등학교', label: '서울고등학교'},
        {value: '소속없음', label: '소속없음'}
      ],
      profileForm : {
        nickName : undefined,
        myDepartment : undefined,
        goal: undefined,
        goalTime: undefined,
      },
      goalTimeOptions : [
        {label: '00:30',value: '00:30'}, {label: '01:00', value: '01:00'}, {label: '01:30', value: '01:30'}, {label: '02:00', value: '02:00'}, {label: '02:30', value: '02:30'}, {label: '03:00', value: '03:00'}, {label: '03:30', value: '03:30'}, {label: '04:00', value: '04:00'}, {label: '04:30', value: '04:30'}, {label: '05:00', value: '05:00'}, {label: '05:30', value: '05:30'}, {label: '06:00', value: '06:00'}, {label: '06:30', value: '06:30'}, {label: '07:00', value: '07:00'}, {label: '07:30', value: '07:30'}, {label: '08:00', value: '08:00'}, {label: '08:30', value: '08:30'}, {label: '09:00', value: '09:00'}, {label: '09:30', value: '09:30'}, {label: '10:00', value: '10:00'}, {label: '10:30', value: '10:30'}, {label: '11:00', value: '11:00'}, {label: '11:30', value: '11:30'}, {label: '12:00', value: '12:00'}, {label: '12:30', value: '12:30'}, {label: '13:00', value: '13:00'}
      ],
      rules: {
        nickName: [
          {
            required: true,
            message: '닉네임을 작성해주세요',
            trigger: 'blur',
          },
          {
            min: 3,
            max: 10,
            message: '3~10 글자 사이의 닉네임을 작성해주세요',
            trigger: 'blur',
          },
        ],
        myDepartment : [
          { required : true, }
        ],
        goalTime: [
          { required: true, }
        ],
        goal: [
          { required: true, message: '다짐을 작성해주세요'}
        ],
      },
    })
    const profileDialogOpen = function () {
      console.log(state.user.isRival)
      console.log(state.user.isMe)
      state.profileForm.myDepartment = state.department[props.user.departmentId]
      state.profileForm.nickName = props.user.nickName
      state.profileForm.goal = props.user.goal

      let tempTime = props.user.goalTime
      let hour = Math.floor((tempTime / 60) >= 1 ? tempTime/60 : 0)+''
      let minute = ((tempTime % 60) >= 1? tempTime%60 : 0) +''
      state.profileForm.goalTime = hour.padStart(2,'0')+':'+minute.padStart(2,'0')

      state.profileEditDialogVisible = true
    }

    const handleClose = function () {
      state.profileEditDialogVisible = false
    }

    const editSubmit = function() {
      if (typeof(state.profileForm.myDepartment) == 'object' ) {
        state.profileForm.myDepartment = state.profileForm.myDepartment[0]
      }

      let tempGoalTime = (state.profileForm.goalTime+'').split(':')
      let hour = tempGoalTime[0] *1
      let minute = tempGoalTime[1] *1


      let time = ((hour*60) + minute) + ''
      let departmentId = (getKeyByValue(state.department, state.profileForm.myDepartment)) *1

      axios({
          url: `users/${state.user.id}`,
          method: 'put',
          data: {
            'departmentId': departmentId,
            'goal': state.profileForm.goal,
            'goalTime': time,
            'id': state.user.id,
            'nickName': state.profileForm.nickName,
            'thumbnail': state.user.thumbnail,
            'userId': state.user.userId
          }
        })
        .then(res => {
          console.log(res)
        })
        .catch(err => {
          console.log(err)
        })
      state.user.nickName = state.profileForm.nickName
      state.user.goalTime = time
      state.user.departmentId = departmentId
      state.user.goal = state.profileForm.goal

      state.profileEditDialogVisible = false
    }

    const getKeyByValue = function(object, value){
      return Object.keys(object).find(key => object[key] === value);
    }
    return {
      state,
      profileDialogOpen,
      editSubmit,
      handleClose
    }
  }

}
</script>


