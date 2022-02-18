<template>
  <div v-if="!state.isLogin" style="background-color:#d0d0d0; margin:5rem; border:5px solid; padding:7rem;">
    <div style="text-align:center">
      <font-awesome-icon style="font-size:2rem; margin-end:10px;" icon="circle-exclamation"/>
      <span style="font-size:2rem; font-weight:bold;">로그인 하시면 나의 공부 정보를 볼 수 있어요!</span>
    </div>
  </div>

  <div v-if="state.isLogin" class="rank-top">
    <el-card class="box-card">
      <div class="container">
        <el-image
          :src="state.tier.imgurl"
          :fit="contain"
        ></el-image>
        <span class="fontBold">{{ state.tier.name }}</span>
      </div>
      <el-progress
        :text-inside="true"
        :stroke-width="24"
        :percentage="state.tier.progress"
        style="margin-top:10px;"
      />
      <div style="margin-top:10px;">
        <span class="fontBold">{{state.tier.nextTier}}</span>
        승격까지 약
        <span class="fontBold">{{ state.tier.necessaryTime }}</span>
        시간 남았습니다.
      </div>
    </el-card>
    <el-card class="box-card-r">
      <ChartWrapper v-if="state.weekStudyTime.length > 0" :weekStudyTime="state.weekStudyTime" />
      <div style="margin-top:10px;">
        <span>수험생님은 {{ state.totalUserCount }}명 중<span class="fontBold" style="margin-left:5px;">{{ state.myRank }}</span> 등 입니다.</span>
      </div>
    </el-card>
  </div>

  <div style="display:flex; justify-content:space-around; align-items:center;">
    <h3>종합 랭킹</h3>
    <h3/><h3/><h3/>
  </div>

  <hr width = "90%"/>

  <div>
    <el-row>
      <el-col :span="2"/>
      <el-col :span="2">
        <el-cascader
            v-model="state.term"
            :options="state.terms"
            @change="fetchRank"
            placeholder="기간"
            style="margin-end:1rem;"
          ></el-cascader>
      </el-col>

      <el-col :span="1"/>
      <el-col :span="2">
        <el-cascader
          v-model="state.category"
          :options="state.categories"
          @change="fetchRank"
          placeholder="카테고리"
          style="margin-end:1rem;"
        ></el-cascader>
      </el-col>

      <el-col :span="1"/>
      <el-col :span="2">
        <el-cascader
          v-model="state.classs"
          :options="state.classes"
          @change="fetchRankClass"
          placeholder="소속"
          style="margin-end:1rem;"
        >
        </el-cascader>
      </el-col>

      <el-col :span="4"/>
      <el-col :span="8">
        <div class="mt-4">
          <el-input
            v-model="state.input"
            placeholder="검색어를 입력하세요"
            class="input-with-select"
            @keyup.enter="searchUser"
          >
            <template #prepend>
              <el-select v-model="state.select" placeholder="닉네임" style="width: 100px">
              </el-select>
            </template>
            <template #append>
              <el-button :icon="Search" @click="searchUser"></el-button>
            </template>
          </el-input>
        </div>
      </el-col>
    </el-row>
    <el-table
      :data="state.rank"
      style="width: 85%; margin-top:30px; left:7.5%"
      :row-style="tableStyle"
      header-row-style = "background-color:rgba(58, 194, 88, 1); color:#fff"
      @row-click="clickMypage"
    >
      <el-table-column prop="#" label="" width="50" />
      <el-table-column type="index" :index="reindex" label="순위" width="100" />
      <el-table-column  prop="nickName" label="닉네임" width="500" />
      <el-table-column  prop="totalStudyTime" label="티어" width="100" >
        <template v-slot="scope">
          <el-image
          style="width:50px; height:50px;"
          :src="tiersrc(scope.row.totalStudyTime)"
          :fit="contain"
        ></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="department" label="소속" />
      <el-table-column prop="totalStudyTime" :formatter="formatter" label="공부한시간" />
    </el-table>

    <el-pagination
      class='pagination'
      style="margin-top: 30px;"
      background="#3AC258"
      layout="prev, pager, next"
      :total="state.pageNum"
      @current-change="setPage"
    ></el-pagination>
  </div>
</template>

<style>
.input-with-select .el-input-group__prepend {
  background-color: rgb(78, 78, 78);
}
.pagination {
  justify-content: center;
}
.rank-top {
  display: flex;
  justify-content: space-around;
  height: 50vh;
}
.box-card {
  display: flex;
  justify-content: center;
  align-items: center;
  width:500px;
}
.box-card .container {
  display: flex;
  justify-content: center;
  align-items: center;
  width:400px;
}
.box-card .container .el-image {
  width: 50px !important;
  height: 50px !important;
}
.box-card .container .el-image img{
  width: 50px !important;
  height: 50px !important;
}
.el-progress-bar__inner {
  background-color: rgba(58, 194, 88, 1);
}
.el-progress-bar__outer {
  background-color: rgba(224, 224, 224, 1);
}
.fontBold {
  font-weight:bold;
  font-size:1.2rem;
}
.box-card-r {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 500px;
}
canvas {
  width:400px;
}
</style>

<script>
import { reactive, computed, onUpdated, onBeforeMount } from 'vue'
import { useStore } from 'vuex'
import axios from 'axios'
import { useRouter } from 'vue-router'
import ChartWrapper from './components/chartWrapper.vue'


export default {
  name: 'Ranking',
  components : {
    ChartWrapper
  },
  setup() {


    const router = useRouter()
    const store = useStore()
    const state = reactive({
      input : null,
      term : null,
      uid : '',
      select : null,
      department : null,
      pageNum: '0',
      currentPage : 1,
      isLogin: computed(() => {
        return store.state['root'].isLogin
      }),
      userId: null,
      myRank: 0,
      totalUserCount: 0,
      tier: {
        name: '',
        progress: 0,
        imgurl: '',
        necessaryTime: 0,
        nextTier: ''
      },
      weekStudyTime: [],
      selects: [
        {
        value: 'userNickname',
        label: '아이디'
        },
        {
        value: 'department',
        label: '소속'
        }
      ],

      terms: [
        {
        value: null,
        label: '전체'
        },
        {
        value: 'day',
        label: '1일'
        },
        {
        value: 'week',
        label: '1주일'
        },
        {
        value: 'month',
        label: '한달'
        }
      ],
      classs : '',
      classes: [
        {
        value: 'all',
        label: '전체'
        },
        {
        value: 'department',
        label: '내 소속'
        },
        {
        value: 'rival',
        label: '라이벌'
        }
      ],

      conditions: [
        {
        value: 'id',
        label: '아이디'
        },
        {
        value: 'department',
        label: '소속'
        }
      ],

      category: '',
      categories: [
        {
        value: null,
        label: '전체'
        },
        {
        value: 'language',
        label: '어학'
        },
        {
        value: 'official',
        label: '공무원'
        },
        {
        value: 'job',
        label: '취업'
        },
        {
        value: 'certificate',
        label: '자격증'
        },
        {
        value: 'college',
        label: '대입'
        },
        {
        value: 'self',
        label: '자율'
        }
      ],
      rank : [],
    })

    onUpdated(() => {
      if (state.isLogin) {
        getEntireStudyTime()
      }
    })

    // 임시
    onBeforeMount(() => {
      if (state.isLogin) {
        getWeekStudyTime()
        getRank()
      }
    })

    const getUser = function () {
      state.userId = localStorage.getItem('userId')
    }

    const clickMypage = (row) => {
      router.push({
        name: 'my-page',
        params: {
          userId: row.userPid
        }
      })
    }

    const getEntireStudyTime = function () {
      getUser()
      axios.get(`users/time/entire/${state.userId}`)
        .then(res => {
          return res.data
        })
        .then(res => {
          const mtoh = res.studyTime / 60
          if (mtoh < 10) {
            state.tier.name = 'Iron'
            state.tier.progress = parseInt((mtoh / 10) * 100)
            state.tier.imgurl = require('../../assets/images/tier/iron.png')
            state.tier.necessaryTime = 10 - parseInt(mtoh)
            state.tier.nextTier = 'Bronze'
          }
          else if (mtoh >= 10 & mtoh < 50) {
            state.tier.name = 'Bronze'
            state.tier.progress = parseInt(((mtoh - 10) / 40) * 100)
            state.tier.imgurl = require('../../assets/images/tier/bronze.png')
            state.tier.necessaryTime = 50 - parseInt(mtoh)
            state.tier.nextTier = 'Silver'

          } else if (mtoh >= 50 & mtoh < 200) {
            state.tier.name = 'Silver'
            state.tier.progress = parseInt(((mtoh - 50) / 150) * 100)
            state.tier.imgurl = require('../../assets/images/tier/silver.png')
            state.tier.necessaryTime = 200 - parseInt(mtoh)
            state.tier.nextTier = 'Gold'

          } else if (mtoh >= 200 & mtoh < 500) {
            state.tier.name = 'Gold'
            state.tier.progress = parseInt(((mtoh - 200) / 300) * 100)
            state.tier.imgurl = require('../../assets/images/tier/gold.png')
            state.tier.necessaryTime = 500 - parseInt(mtoh)
            state.tier.nextTier = 'Diamond'

          } else if (mtoh >= 500) {
            state.tier.name = 'Diamond'
            state.tier.progress = 100
            state.tier.imgurl = require('../../assets/images/tier/diamond.png')
            state.tier.necessaryTime = 0
            state.tier.nextTier = ''

          } else {
            state.tier.name = 'Iron'
            state.tier.progress = 0
            state.tier.imgurl = require('../../assets/images/tier/iron.png')
            state.tier.necessaryTime = 10
            state.tier.nextTier = 'Bronze'
          }
        })
    }

    const getWeekStudyTime = function () {
      getUser()
      axios.get(`users/time/week/?id=${state.userId}`)
        .then(res => {
          return res.data
        })
        .then(res => {
          for (let i in res) {
            const tmp = [
              res[i].date, res[i].studyTime
            ]
            state.weekStudyTime.push(tmp)
          }
          console.log(state.weekStudyTime)
        })
        .catch(err => {
          alert(err)
        })
    }

    const getRank = function () {
      getUser()
      axios.get(`ranking/rank?userId=${state.userId}`)
        .then(res => {
          return res.data
        })
        .then(res => {
          state.myRank = res.ranking
          state.totalUserCount = res.totalUserCount
        })
    }

    return {
      state,
      getWeekStudyTime,
      clickMypage
    }
  },

//userClass&term&category&userNickname&userId(숫자))
  methods: {
    fetchRankClass(){
      //getUser()
      let url = 'ranking?userClass='+this.state.classs+'&userId='+this.state.userId
      axios.get(url)
      .then(res => {
        this.state.rank= res.data.content;
        this.state.pageNum = parseInt(res.data.totalElements/20+1)*10;
      })
      .catch((Error)=>{
      console.log(Error);
    })

    }
    ,
    tiersrc(value){
      let hour = value / 60;

      if(hour<10){
        return require('../../assets/images/tier/iron.png')
      }else if(hour<50){
        return require('../../assets/images/tier/bronze.png')
      }else if(hour<200){
        return require('../../assets/images/tier/silver.png')
      }else if(hour<500){
        return require('../../assets/images/tier/gold.png')
      }else {
        return require('../../assets/images/tier/diamond.png')
      }

    }


    ,
    formatter(value){
      let hour = parseInt(value.totalStudyTime / 60)
      let min = value.totalStudyTime%60
      console.log(value)
      if(hour==0){
        return min+'분';
      }else{
        return hour+'시간 '+min+'분'
      }
    }
    ,

    reindex(index){
      index = parseInt(this.state.currentPage-1)*20+index+1;
      return index;
    },
    fetchRank(url){
      url = 'ranking/?';
      if(this.state.input!=null){
        url += 'userNickname='+this.state.input+'&';
      }
      if(this.state.term!=null){
        url += 'term='+this.state.term+'&';
      }
      if(this.state.classs!=null){
        url += 'userClass='+this.state.classs+'&';
      }
      if(this.state.category!=null){
        url += 'category='+this.state.category+'&';
      }
      axios.get(url).then((Response)=>{
      this.state.rank = Response.data.content;
      this.state.pageNum = parseInt(Response.data.totalElements/20+1)*10;
    }).catch((Error)=>{
      console.log(Error);
    }
    )},

    searchUser(){
      axios.get('ranking/?userNickname='+this.state.input).then((Response)=>{
        this.state.rank = Response.data.content;
        }).catch((Error)=>{
        console.log(Error);
        }
        )},

    changeTerm(){
      console.log(this.state.term);
      const url = 'ranking?term='+this.state.term;
      this.fetchRank(url);
    },

    setPage(page){
      this.state.currentPage = page;
      let url = 'ranking/?';
      if(this.state.input!=null){
        url += 'userNickname='+this.state.input+'&';
      }
      if(this.state.term!=null){
        url += 'term='+this.state.term+'&';
      }
      if(this.state.classs!=null){
        url += 'userClass='+this.state.classs+'&';
      }
      if(this.state.category!=null){
        url += 'category='+this.state.category+'&';
      }
      url+= 'page='+page;
      axios.get(url).then((Response)=>{
    this.state.rank = Response.data.content;
    this.state.pageNum = parseInt(Response.data.totalElements/20+1)*10;
    }).catch((Error)=>{
    console.log(Error);
    }
    )},

     tableStyle({rowIndex}) {
      if(rowIndex%2===1){
        return{
          cursor:'pointer',
          background : '#FAEEE7',
          height : '30px'
        }
      }else{
        return{
          cursor : 'pointer',
          height : '30px'
        }
      }
    }
  },

  created () {
    this.fetchRank('ranking');
  },

}
</script>
