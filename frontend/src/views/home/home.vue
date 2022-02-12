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
      <el-dialog v-model="state.createStudyDialogVisible" title="방 생성" width="45%" center>
        <hr style="margin-top:-20px;">
        <el-form :model="state.form" :rules="state.rules" label-width="120px">
          <div style="display:flex;">
            <div style="margin-end:50px;">
              <el-form-item label="방 이름" prop="name">
                <el-input v-model="state.form.name" placeholder="방 제목을 작성해주세요"></el-input>
              </el-form-item>
              <el-form-item label="방 최대 인원" prop="number">
                <el-input-number v-model="state.form.number" :min="1" :max="6" />
              </el-form-item>
              <div>
                <el-form-item label="카테고리" prop="category">
                  <el-radio-group v-model="state.form.category">
                    <el-radio :label="1">어학</el-radio>
                    <el-radio :label="2">공무원</el-radio>
                    <el-radio :label="3">취업</el-radio>
                    <el-radio :label="4">대입</el-radio>
                    <el-radio :label="5">자격증</el-radio>
                    <el-radio :label="6">자율</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="방 설명">
                  <el-input v-model="state.form.desc" type="textarea"></el-input>
                </el-form-item>
              </div>
            </div>
            <el-image
              style="width: 120px; height: 120px; cursor:pointer;"
              :src="state.imgList[state.form.thumbnail].imgUrl"
              :fit="contain"
              @click="state.ThumbnailDialogVisible = true"
            ></el-image>
            <div style="text-align:center">
              <el-dialog
                v-model="state.ThumbnailDialogVisible"
                title="썸네일 선택"
                width="30%"
              >
                <div style="text-align:center;">
                  <div v-for="i in state.imgList" :key="i.imgNum" class="inblk">
                    <el-card class="box-card">
                      <el-image
                        style="width: 120px; height: 120px"
                        :src="i.imgUrl"
                        :fit="contain"
                        @click="pickThumbnail(i.imgNum)"
                      ></el-image>
                    </el-card>
                  </div>
                </div>
              </el-dialog>
            </div>
          </div>
          <el-alert
            v-if="state.alertVisible"
            title="방 제목을 입력해주세요!"
            type="warning"
            center
            style="margin-bottom:20px;"
          />
          <div style="display:flex; justify-content:end;">
            <el-button round plain @click="state.createStudyDialogVisible = false">취소</el-button>
            <el-button round plain type="success" @click="onSubmit()">방 생성</el-button>
          </div>
        </el-form>
      </el-dialog>
    </div>
  </div>
  <div style="display:flex; justify-content:center; height: 50rem !important;">
    <ul class="infinite-list" v-infinite-scroll="load" style="overflow:auto">
      <li v-for="i in state.studyList" @click="clickConference(i.roomId)" class="infinite-list-item" :key="i.roomId" >
        <conference
          :title="i.roomTitle"
          :category="`#${state.options[i.roomCategory].label}`"
          :thumbnail="i.roomThumbnail"
          :headCount="i.headCount"
          :maxPeople="i.roomMaxPeople"
        />
      </li>
    </ul>
  </div>
</template>

<style scoped>
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
.el-card {
    --el-card-border-color: var(--el-border-color-light, #ebeef5);
    --el-card-border-radius: 10px;
    --el-card-padding: 10px;
}
.el-card:hover {
  --el-card-bg-color: rgba(58, 194, 88, 1);
}
.box-card {
  width: auto;
  height: auto;
  cursor: pointer;
}
.inblk {
  display: inline-block;
}
</style>

<script>
import Conference from './components/conference'
import { reactive, onMounted, onUpdated } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import axios from 'axios'

export default {
  name: 'Home',

  components: {
    Conference,
  },

  setup () {
    const router = useRouter()
    const store = useStore()
    const state = reactive({
      count: store.state.root.studyroomListLength,
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
        thumbnail: 0,
        name: '',
        number: 0,
        category: 1,
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
          }
        ],
        category: [
          {
            required: true,
          }
        ],
      },
      imgList: [
        {
          imgNum: '0',
          imgUrl: require('../../assets/images/thumbnail/0.png')
        },
        {
          imgNum: '1',
          imgUrl: require('../../assets/images/thumbnail/1.png')
        },
        {
          imgNum: '2',
          imgUrl: require('../../assets/images/thumbnail/2.png')
        },
        {
          imgNum: '3',
          imgUrl: require('../../assets/images/thumbnail/3.png')
        },
        {
          imgNum: '4',
          imgUrl: require('../../assets/images/thumbnail/4.png')
        },
        {
          imgNum: '5',
          imgUrl: require('../../assets/images/thumbnail/5.png')
        },
        {
          imgNum: '6',
          imgUrl: require('../../assets/images/thumbnail/6.png')
        },
        {
          imgNum: '7',
          imgUrl: require('../../assets/images/thumbnail/7.png')
        },
        {
          imgNum: '8',
          imgUrl: require('../../assets/images/thumbnail/8.png')
        }
      ],
      createStudyDialogVisible: false,
      ThumbnailDialogVisible: false,
      studyList: [],
      alertVisible: false
    })

    onMounted(() => {
      studyListData()
    })

    onUpdated(() => {
      resetDialog()
    })

    const load = function () {
      if (state.count <= store.state.root.studyroomListLength) {
        state.count += 10
      }
    }

    const clickConference = function (id) {
      router.push({
        name: 'studyroom-standby',
        params: {
          studyroomId: id
        }
      })
    }

    const studyListData = function () {
      axios.get('rooms')
        .then(res => {
          state.studyList = res.data
        })
        .catch(err => {
          console.log(err)
        })
    }

    const resetDialog = function () {
      if (!state.createStudyDialogVisible) {
        state.form.thumbnail = 0
        state.form.name = ''
        state.form.number = 0
        state.form.category = 1
        state.form.desc = ''
        state.alertVisible = false
      }
    }

    const pickThumbnail = function (imgNum) {
      state.form.thumbnail = imgNum
      state.ThumbnailDialogVisible = false
    }

    const onSubmit = function () {
      if (!state.form.name) {
        state.alertVisible = true
        return
      }
      axios({
        url: 'rooms',
        method: 'post',
        data: {
          'roomTitle': state.form.name,
          'roomMaxPeople': state.form.number,
          'roomDescription': state.form.desc,
          'roomThumbnail': `${state.form.thumbnail}`,
          'roomCategory': state.form.category
        }
      })
        .then(res => {
          router.push({
            name: 'studyroom-standby',
            params: {
              studyroomId: res.data.id
            }
          })
        })
        .catch(err => {
          console.log(err)
        })
    }

    return {
      state,
      load,
      clickConference,
      studyListData,
      resetDialog,
      pickThumbnail,
      onSubmit,
    }
  }
}
</script>
