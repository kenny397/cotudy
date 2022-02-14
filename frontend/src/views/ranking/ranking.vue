
<template>
  <div v-if="true" style="background-color:#d0d0d0; margin:5rem; border:5px solid; padding:7rem;">
      <h2 style="text-align:center">로그인 하시면 나의 공부 정보를 볼 수 있어요!</h2>
  </div>
  <div style="display:flex; justify-content:space-around; align-items:center;">
    <h3>종합 랭킹</h3>
    <h3/><h3/><h3/>
  </div>
  <hr width = "90%"/>
  <div>
    <el-row>
      <el-col :span="2"/>
    <el-col :span="2"> <el-select v-model="state.term" @change="changeTerm" class="m-2" placeholder="전체">
    <el-option
      v-for="item in state.terms"
      :key="item.value"
      :label="item.label"
      :value="item.value"
    >
    </el-option>
  </el-select>
  </el-col>
    <el-col :span="1"/>
    <el-col :span="2">    <el-select v-model="state.category" @change="fetchRank" class="m-2" placeholder="전체">
    <el-option
      v-for="item in state.categories"
      :key="item.value"
      :label="item.label"
      :value="item.value"
    >
    </el-option>
  </el-select></el-col>
  <el-col :span="1"/>
    <el-col :span="2">
    <el-select v-if=false v-model="state.classs" @change="fetchRank" class="m-2" placeholder="전체">
    <el-option
      v-for="item in state.classes"
      :key="item.value"
      :label="item.label"
      :value="item.value"
    >
    </el-option>
  </el-select></el-col>



<el-col :span="4"/>
    <el-col :span="8">
<div class="mt-4">
    <el-input
      v-model="state.input"
      placeholder="검색어를 입력하세요"
      class="input-with-select"
    >
      <template #prepend>
        <el-select v-model="state.select" placeholder="아이디" style="width: 100px">
          <el-option label="아이디" value="uid"></el-option>
          <el-option label="소속" value="department"></el-option>
        </el-select>
      </template>
      <template #append>
        <el-button :icon="Search" @click="searchUser" @keyup:enter="searchUser"></el-button>
      </template>
    </el-input>
  </div>

    </el-col>
  </el-row>
  <el-table :data="state.rank" stripe style="width: 85%; margin-top:30px; left:7.5%" :row-style="tableStyle"
  header-row-style = "background-color:rgba(58, 194, 88, 1); color:#fff" >
    <el-table-column prop="#" label="" width="50" />
    <el-table-column type="index"  label="순위" width="100" />
    <el-table-column prop="nickName" label="닉네임" width="500" />
    <el-table-column prop="department" label="소속" />
    <el-table-column prop="totalStudyTime" label="공부한시간" />
  </el-table>

  </div>

</template>

<script>
//import { Search } from '@element-plus/icons-vue'
//import { setupElementPlus } from './element-plus.js'
//setupElementPlus()
import axios from 'axios';
import { reactive} from 'vue'
export default {
  name: 'Ranking',

  components: {

  },


  setup() {
    const state = reactive({
      input : null,
      term : null,
      uid : '',
      select : null,
      department : null,

      terms : [{
        value : null,
        label : '전체'
      },{
        value : 'day',
        label : '1일'
      },{
        value : 'week',
        label : '1주일'
      },{
        value : 'month',
        label : '한달'
      },],
      classs : '',
      classes : [{
        value : 'all',
        label : '전체'
      },{
        value : 'department',
        label : '내 소속'
      },{
        value : 'rival',
        label : '친구'
      },],

      conditions : [{
        value : 'id',
        label : '아이디'
      },{
        value : 'department',
        label : '소속'
      },],

      category : '',
      categories :[{
        value : null,
        label : '전체'
      },{
        value : 'language',
        label : '어학'
      },{
        value : 'official',
        label : '공무원'
      },{
        value : 'job',
        label : '취업'
      },{
        value : 'certificate',
        label : '자격증'
      },{
        value : 'college',
        label : '대입'
      },{
        value : 'self',
        label : '자율'
      },],


      rank : [],

    });



    return {
      state,
    }
  },

//userClass&term&category&userNickname&userId(숫자))
  methods: {
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
    console.log(this.state.rank);
    }).catch((Error)=>{
    console.log(Error);
    }
    )},
//https://i6b105.p.ssafy.io/api/v1/ranking/?userNickname=test
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



     tableStyle({rowIndex}) {
      if(rowIndex%2===1){
        return{
          background : '#FAEEE7',
          height : '30px'
        }
      }else{
        return{
          height : '30px'
        }
      }
    }
  },

  created(){
    this.fetchRank('ranking');
  },



}
</script>



<style scoped>
.input-with-select .el-input-group__prepend {
  background-color: rgb(78, 78, 78);
}



/* .el-table  {
  --el-table-tr-bg-color: var(--el-color-success-lighter);
} */
</style>
