
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
    <el-col :span="2"> <el-select v-model="term" class="m-2" placeholder="전체">
    <el-option
      v-for="item in terms"
      :key="item.value"
      :label="item.label"
      :value="item.value"
    >
    </el-option>
  </el-select>
  </el-col>
  <el-col :span="1"/>
    <el-col :span="2">    <el-select v-model="classs" class="m-2" placeholder="전체">
    <el-option
      v-for="item in classes"
      :key="item.value"
      :label="item.label"
      :value="item.value"
    >
    </el-option>
  </el-select></el-col>
<el-col :span="7"/>
    <el-col :span="8">
<div class="mt-4">
    <el-input
      v-model="input"
      placeholder="검색어를 입력하세요"
      class="input-with-select"
    >
      <template #prepend>
        <el-select v-model="select" placeholder="아이디" style="width: 100px">
          <el-option label="아이디" value="uid"></el-option>
          <el-option label="소속" value="department"></el-option>
        </el-select>
      </template>
      <template #append>
        <el-button :icon="Search" v-on:click="searchUser()"></el-button>
      </template>
    </el-input>
  </div>

    </el-col>
  </el-row>
  <el-table :data="rank" stripe style="width: 85%; margin-top:30px; left:7.5%" :row-class-name="tableRowClassName"
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
export default {
  name: 'Ranking',

  components: {

  },

  data(){
    return {
      input : '',
      term : '',
      uid : '',
      department : '',

      terms : [{
        value : 'whole',
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

      select : '',
      conditions : [{
        value : 'id',
        label : '아이디'
      },{
        value : 'department',
        label : '소속'
      },],

      rank : []
    }
  },


  methods: {
    fetchRank(url){
      axios.get(url).then((Response)=>{
    this.rank = Response.data.content;


    }).catch((Error)=>{
    console.log(Error);
    }
    )},

    searchUser(){

    }


  },

  created(){
    this.fetchRank('ranking');
  },

  watch : {
      term : function(val){
        const url = 'ranking?'+val;
        this.fetchRank(url);
      }
  }

}
</script>



<style scoped>
.input-with-select .el-input-group__prepend {
  background-color: rgb(78, 78, 78);
}


</style>
