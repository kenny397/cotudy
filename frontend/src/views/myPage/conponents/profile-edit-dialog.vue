<template>
  <el-button
      round
      style="background: #3AC258; float:right; color:white; height:50px; font-size:20px;"
      @click="profileDialogOpen()"
    >
      <font-awesome-icon icon="pen-to-square" style="float:right; font-size:20px;"/>
      <span style="margin-left : 5px;">회원정보 수정</span>
  </el-button>
  <el-dialog
    v-model="state.profileEditDialogVisible"
    title="회원정보 수정"
    width="30%"
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
        <el-select v-model="state.profileForm.myDepartment" class="m-2" placeholder="Select">
          <el-option
            v-for="department in state.department"
            :key="department"
            :label="department"
            :value="department"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="목표 공부시간" prop="goalTime">
        <el-time-select
          v-model="state.profileForm.goalTime"
          start="00:30"
          step="00:30"
          end="17:00"
        >
        </el-time-select>
      </el-form-item>
      <el-form-item label="나의 다짐" prop="goal">
        <el-input
          v-model="state.profileForm.goal"
          autocomplete="off"
        ></el-input>
      </el-form-item>

      <el-button @click="editSubmit()">수정</el-button>
      <el-button>취소</el-button>

    </el-form>
  </el-dialog>
</template>

<style scoped>

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
        2: '멀티캠퍼스',
        3: '삼성전자',
        4: '삼성생명',
        5: '삼성SDS',
        10: '소속없음',
      },
      profileForm : {
        nickName : undefined,
        myDepartment : undefined,
        goal: undefined,
        goalTime: undefined,
      },
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
          { required: true, }
        ],
      },
    })
    const profileDialogOpen = function () {
      state.profileForm.myDepartment = state.department[props.user.departmentId]
      state.profileForm.nickName = props.user.nickName
      state.profileForm.goal = props.user.goal

      let tempTime = props.user.goalTime
      let hour = Math.floor((tempTime / 60) >= 1 ? tempTime/60 : 0)+''
      let minute = ((tempTime % 60) >= 1? tempTime%60 : 0) +''
      state.profileForm.goalTime = hour.padStart(2,'0')+':'+minute.padStart(2,'0')

      state.profileEditDialogVisible = true
    }

    const editSubmit = function() {
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
      state.user.goalTime = tempGoalTime[0] +'시간 '+ tempGoalTime[1]+'분'
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
      editSubmit
    }
  }

}
</script>


