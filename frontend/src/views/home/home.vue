<template>
  <div style="margin:5rem; border:5px solid; padding:7rem;">
    <h2 style="text-align:center">언제 어디서나 최고의 공부 환경을 느껴보세요!</h2>
  </div>
  <div style="display:flex; justify-content:space-around; align-items:center;">
    <h3>Study List</h3>
    <div>
      <span style="margin-end:5px">Category: </span>
      <el-cascader
        v-model="value"
        :options="state.options"
        style="margin-end:1rem;"
      ></el-cascader>
      <el-button plain round type="success" @click="state.createStudyDialogVisible = true">Create Study</el-button>
      <el-dialog v-model="state.createStudyDialogVisible" title="방생성" width="30%" center>
        <hr>
        <el-form ref="formRef" :model="state.form" :rules="state.rules" label-width="120px">
          <el-form-item label="방제목" prop="name">
            <el-input v-model="state.form.name" placeholder="방 제목을 작성해주세요"></el-input>
          </el-form-item>
          <el-form-item label="방 최대 인원" prop="number">
            <el-select v-model="state.form.number">
              <el-option label="1" value="1"></el-option>
              <el-option label="2" value="2"></el-option>
              <el-option label="3" value="3"></el-option>
              <el-option label="4" value="4"></el-option>
              <el-option label="5" value="5"></el-option>
              <el-option label="6" value="6"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="방 공개여부">
            <el-switch v-model="state.form.delivery"></el-switch>
          </el-form-item>
          <el-form-item label="카테고리" prop="type">
            <el-checkbox-group v-model="state.form.type">
              <el-checkbox label="어학" name="type"></el-checkbox>
              <el-checkbox label="공무원" name="type"></el-checkbox>
              <el-checkbox label="취업" name="type"></el-checkbox>
              <el-checkbox label="자격증" name="type"></el-checkbox>
              <el-checkbox label="대입" name="type"></el-checkbox>
              <el-checkbox label="자율" name="type"></el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="방 설명">
            <el-input v-model="state.form.desc" type="textarea"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button plain @click="state.createStudyDialogVisible = false">Cancel</el-button>
            <el-button plain type="success" @click="onSubmit">Create</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
  </div>
  <div style="display:flex; justify-content:center; height: 50rem !important;">
    <ul class="infinite-list" v-infinite-scroll="load" style="overflow:auto">
      <li v-for="i in state.count" @click="clickConference(i)" class="infinite-list-item" :key="i" >
        <conference />
      </li>
    </ul>
  </div>
</template>

<style>
/* .infinite-list {
  padding-left: 0;
  max-height: calc(100% - 35px);
}

@media (min-width: 701px) and (max-width: 1269px) {
  .infinite-list {
    min-width: 700px;
  }
}

@media (min-width: 1270px) {
  .infinite-list {
    min-width: 1021px;
  }
}

.infinite-list .infinite-list-item {
  min-width: 335px;
  max-width: 25%;
  display: inline-block;
  cursor: pointer;
} */

.infinite-list {
  padding-left: 0;
  width: 1200px;
  max-height: calc(100% - 35px);
}
.infinite-list-item {
  width: 20%;
  display: inline-block;
  cursor: pointer;
}
.dialog-footer button:first-child {
  margin-right: 10px;
}
.el-switch.is-checked .el-switch__core {
    border-color: rgba(58, 194, 88, 1);
    background-color: rgba(58, 194, 88, 1);
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
</style>

<script>
import Conference from './components/conference'
import { reactive } from 'vue'
import { useRouter } from 'vue-router'

export default {
  name: 'Home',

  components: {
    Conference,
  },

  setup () {
    const router = useRouter()

    const state = reactive({
      count: 20,
      options: [
        {
        value: 'linguistic',
        label: '어학',
        },
        {
        value: 'civil service examination',
        label: '공무원',
        },
        {
        value: 'job seeker',
        label: '취업',
        },
        {
        value: 'certificate',
        label: '자격증',
        },
        {
        value: 'national college entrance exam',
        label: '대입',
        },
        {
        value: 'self-directed learning',
        label: '자율',
        },
      ],
      form: {
        name: '',
        number: '',
        delivery: false,
        type: [],
        desc: '',
      },
      rules: {
        name: [
          {
            required: true,
            message: '방 제목을 작성해주세요',
            trigger: 'blur',
          },
          {
            min: 3,
            max: 20,
            message: '3~20 글자 사이의 제목을 작성해주세요',
            trigger: 'blur',
          },
        ],
        number: [
          {
            required: true,
            message: '방 최대 인원을 선택해 주세요',
            trigger: 'blur',
          },
        ],
        type: [
          {
            type: 'array',
            required: true,
            message: '하나 이상의 카테고리를 선택해 주세요',
            trigger: 'blur',
          },
        ],
      },
      createStudyDialogVisible: false
    })

    const load = function () {
      state.count += 10
    }

    const clickConference = function (id) {
      router.push({
        name: 'studyroom-detail',
        params: {
          studyroomId: id
        }
      })
    }

    return { state, load, clickConference }
  }
}
</script>
